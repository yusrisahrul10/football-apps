package com.dscunikom.android.footballmatchschedule.fragment.search.team

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.SearchView
import com.dscunikom.android.footballmatchschedule.R
import com.dscunikom.android.footballmatchschedule.activity.teamdetail.TeamDetailActivity
import com.dscunikom.android.footballmatchschedule.adapter.TeamAdapter
import com.dscunikom.android.footballmatchschedule.api.ApiRepository
import com.dscunikom.android.footballmatchschedule.model.Team
import com.dscunikom.android.footballmatchschedule.utils.invisible
import com.dscunikom.android.footballmatchschedule.utils.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_team_search.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.startActivity
import kotlin.Exception

class TeamSearchFragment : Fragment(), TeamSearchView {

    private lateinit var presenter: TeamSearchPresenter
    private var team: MutableList<Team> = mutableListOf()
    private lateinit var teamAdapter: TeamAdapter
    private lateinit var listTeam: RecyclerView

    override fun showLoading() {
        pbTeamSearch?.visible()
    }

    override fun hideLoading() {
        pbTeamSearch?.invisible()
    }

    override fun showTeamList(data: MutableList<Team>) {
        team.clear()
        team.addAll(data)
        teamAdapter.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        with(activity as AppCompatActivity) {
            setSupportActionBar(toolbar_team)
        }
        presenter = TeamSearchPresenter(
            this,
            ApiRepository(),
            Gson()
        )
        teamAdapter = TeamAdapter(team) {
            startActivity<TeamDetailActivity>(TeamDetailActivity.INTENT_DETAIL to it)
        }
        listTeam = find(R.id.rvTeam)
        listTeam.layoutManager = LinearLayoutManager(ctx)
        listTeam.adapter = teamAdapter

        presenter.getTeam("Spanish%20La%20Liga")
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.search_view_menu, menu)

        val searchView = menu?.findItem(R.id.mnSearchView)
        val searchTeam = searchView?.actionView as SearchView

        searchTeam.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                presenter.getTeamSearch(query.toString())
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                try {
                    presenter.getTeamSearch(query.toString())
                } catch (e : Exception) {
                    Log.e("Error", e.toString())
                }
                return true
            }

        })
    }
}