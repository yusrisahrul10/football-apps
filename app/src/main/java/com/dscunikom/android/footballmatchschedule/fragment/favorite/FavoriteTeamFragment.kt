package com.dscunikom.android.footballmatchschedule.fragment.favorite

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dscunikom.android.footballmatchschedule.R
import com.dscunikom.android.footballmatchschedule.activity.teamdetail.TeamDetailActivity
import com.dscunikom.android.footballmatchschedule.adapter.TeamAdapter
import com.dscunikom.android.footballmatchschedule.database.database
import com.dscunikom.android.footballmatchschedule.model.Team
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class FavoriteTeamFragment : Fragment(), AnkoComponent<Context> {

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                    R.color.colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light
                )

                recyclerView {
                    id = R.id.list_team_favorite
                    lparams(width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                }
            }
        }
    }

    private var favorites: MutableList<Team> = mutableListOf()
    private lateinit var adapter: TeamAdapter
    private lateinit var listFavorite: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = TeamAdapter(favorites) {
            ctx.startActivity<TeamDetailActivity>(TeamDetailActivity.INTENT_DETAIL to it)
        }

        listFavorite = find(R.id.list_team_favorite)
        listFavorite.adapter = adapter
        showFavorite()
        swipeRefresh.onRefresh {
            favorites.clear()
            showFavorite()
        }
    }

    override fun onResume() {
        super.onResume()
        favorites.clear()
        showFavorite()
    }

    private fun showFavorite() {
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(Team.TABLE_FAVORITE_TEAMS)
            val favorite = result.parseList(classParser<Team>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }
}