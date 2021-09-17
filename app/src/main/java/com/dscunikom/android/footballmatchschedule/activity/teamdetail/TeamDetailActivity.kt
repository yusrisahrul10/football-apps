package com.dscunikom.android.footballmatchschedule.activity.teamdetail

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import com.dscunikom.android.footballmatchschedule.R
import com.dscunikom.android.footballmatchschedule.adapter.PlayerAdapter
import com.dscunikom.android.footballmatchschedule.api.ApiRepository
import com.dscunikom.android.footballmatchschedule.database.database
import com.dscunikom.android.footballmatchschedule.model.Player
import com.dscunikom.android.footballmatchschedule.model.Team
import com.dscunikom.android.footballmatchschedule.utils.invisible
import com.dscunikom.android.footballmatchschedule.utils.visible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_team_detail.*
import kotlinx.android.synthetic.main.fragment_team_overview.*
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.nestedScrollView
import org.jetbrains.anko.support.v4.startActivity

class TeamDetailActivity : AppCompatActivity() {

    companion object {
        const val INTENT_DETAIL = "EXTRA_DETAIL"
    }

    private lateinit var team: Team
    private var isFavorite: Boolean = false
    private var menuItem: Menu? = null

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        setSupportActionBar(toolbarDetail)

        initData()
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        view_pager.adapter = mSectionsPagerAdapter
        view_pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
        tab_layout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(view_pager))

        favoriteState()
    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> return OverviewFragment.newInstance(
                    team.strDescriptionEN.toString()
                )
                1 -> return PlayerFragment.newInstance(
                    team.strTeam.toString()
                )
                else -> {
                    return OverviewFragment.newInstance(
                        team.strDescriptionEN.toString()
                    )
                }
            }

        }

        override fun getCount(): Int {
            return 2
        }
    }

    fun initData() {
        team = intent.getParcelableExtra(INTENT_DETAIL)

        if(team.strTeamBadge.isNullOrEmpty()) {
            ivTeam.setImageResource(R.drawable.no_image)
        } else {
            Picasso.get().load(team.strTeamBadge).into(ivTeam)
        }

        tvName.text = team.strTeam
        tvYear.text = team.intFormedYear
        tvStadium.text = team.strStadium
    }

    class OverviewFragment : Fragment() {

        companion object {
            private const val EXTRA_PARAM = "EXTRA_PARAM"

            fun newInstance(args: String): OverviewFragment {
                val fragment =
                    OverviewFragment()
                fragment.arguments = bundleOf(EXTRA_PARAM to args)

                return fragment
            }
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.fragment_team_overview, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            tvOverview?.text = arguments?.getString(EXTRA_PARAM)
        }
    }

    class PlayerFragment : Fragment(), TeamPlayerView, AnkoComponent<Context> {
        override fun createView(ui: AnkoContext<Context>): View = with(ui) {
            relativeLayout {
                recyclerView {
                    id = R.id.list_player
                    layoutManager = GridLayoutManager(ctx, 3)
                }.lparams(width = matchParent, height = matchParent)
                mainProgressBarPlayer = progressBar {
                    id = Ids.mainProgressBarPlayer
                }.lparams(width = wrapContent, height = wrapContent) {
                    centerInParent()
                }
            }
        }

        private object Ids {
            val mainProgressBarPlayer = 1
        }

        override fun showLoading() {
            mainProgressBarPlayer.visible()
        }

        override fun hideLoading() {
            mainProgressBarPlayer.invisible()
        }

        override fun showPlayerList(data: MutableList<Player>) {
            player.clear()
            player.addAll(data)
            playerAdapter.notifyDataSetChanged()
        }

        companion object {
            private const val EXTRA_DETAIL = "EXTRA_DETAIL"

            fun newInstance(args: String): PlayerFragment {
                val fragment =
                    PlayerFragment()
                fragment.arguments = bundleOf(EXTRA_DETAIL to args)

                return fragment
            }
        }

        private lateinit var presenter: TeamPlayerPresenter
        private var player: MutableList<Player> = mutableListOf()
        private lateinit var playerAdapter: PlayerAdapter
        private lateinit var listPlayer: RecyclerView
        private lateinit var mainProgressBarPlayer: ProgressBar

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return createView(AnkoContext.create(ctx))
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            presenter = TeamPlayerPresenter(
                this,
                ApiRepository(),
                Gson()
            )

            playerAdapter = PlayerAdapter(player) {
                startActivity<PlayerDetailActivity>(PlayerDetailActivity.INTENT_DETAIL to it)
            }

            listPlayer = find(R.id.list_player)
            listPlayer.adapter = playerAdapter
            listPlayer.addItemDecoration(DividerItemDecoration(ctx, DividerItemDecoration.VERTICAL))

            presenter.getPlayer(arguments?.getString(EXTRA_DETAIL).toString())
        }
    }

    private fun favoriteState() {
        database.use {
            val result = select(Team.TABLE_FAVORITE_TEAMS)
                .whereArgs(
                    Team.ID_TEAM + " = {id}",
                    "id" to team.idTeam.toString()
                )
            val favorite = result.parseList(classParser<Team>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            R.id.add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(
                    Team.TABLE_FAVORITE_TEAMS,
                    Team.ID_TEAM to team.idTeam,
                    Team.STR_TEAM_BADGE to team.strTeamBadge,
                    Team.STR_TEAM to team.strTeam,
                    Team.INT_FORMED_YEAR to team.intFormedYear,
                    Team.STR_STADIUM to team.strStadium,
                    Team.STR_DESCRIPTION_EN to team.strDescriptionEN
                )
            }

        } catch (e: SQLiteConstraintException) {
            Log.e("Failed", "Insert Failed")
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(
                    Team.TABLE_FAVORITE_TEAMS, Team.ID_TEAM + " = {id}",
                    "id" to team.idTeam.toString()
                )
            }
        } catch (e: SQLiteConstraintException) {
            Log.e("Failed", "Delete Failed")
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }
}
