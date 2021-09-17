package com.dscunikom.android.footballmatchschedule.fragment.match

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.dscunikom.android.footballmatchschedule.R
import com.dscunikom.android.footballmatchschedule.activity.matchdetail.MatchDetailActivity
import com.dscunikom.android.footballmatchschedule.activity.main.MainPresenter
import com.dscunikom.android.footballmatchschedule.activity.main.MainView
import com.dscunikom.android.footballmatchschedule.adapter.MatchAdapter
import com.dscunikom.android.footballmatchschedule.api.ApiRepository
import com.dscunikom.android.footballmatchschedule.model.League
import com.dscunikom.android.footballmatchschedule.model.LeagueResponse
import com.dscunikom.android.footballmatchschedule.model.Match
import com.dscunikom.android.footballmatchschedule.utils.invisible
import com.dscunikom.android.footballmatchschedule.utils.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.startActivity

class PastMatchFragment : Fragment(), MainView, AnkoComponent<Context> {
    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            orientation = LinearLayout.VERTICAL

            relativeLayout {
                linearLayout {
                    backgroundResource = R.color.colorAccent
                    id = Ids.spinner_container

                    spinnerPrev = spinner {
                        id = Ids.spinner_prev
                    }.lparams(width = matchParent, height = wrapContent)
                }.lparams(width = matchParent, height = wrapContent)
                recyclerView {
                    id = R.id.list_past_match
                    layoutManager = LinearLayoutManager(ctx)
                }.lparams(width = matchParent, height = matchParent) {
                    below(Ids.spinner_container)
                }
                mainProgressBarPrev = progressBar {
                    id = Ids.main_progress_bar_prev
                }.lparams(width = wrapContent, height = wrapContent) {
                    centerInParent()
                }
            }.lparams(width = matchParent, height = wrapContent)
        }
    }

    private object Ids {
        val main_progress_bar_prev = 2
        val spinner_prev = 4
        val spinner_container = 5
    }

    override fun showLeagueList(data: LeagueResponse) {
        spinnerPrev.adapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, data.leagues)

        spinnerPrev.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                league = spinnerPrev.selectedItem as League
                presenter.getMatchPast(league.idLeague.toString())
            }

        }
    }

    override fun showLoading() {
        mainProgressBarPrev.visible()
    }

    override fun hideLoading() {
        mainProgressBarPrev.invisible()
    }

    override fun showMatchList(data: List<Match>) {

        teams.clear()
        teams.addAll(data)
        matchAdapter.notifyDataSetChanged()
    }


    private var teams: MutableList<Match> = mutableListOf()
    private lateinit var presenter: MainPresenter
    private lateinit var matchAdapter: MatchAdapter
    private lateinit var league: League
    private lateinit var listMatch: RecyclerView
    private lateinit var spinnerPrev: Spinner
    private lateinit var mainProgressBarPrev: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return createView(AnkoContext.create(ctx))
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = MainPresenter(this, ApiRepository(), Gson())

        matchAdapter = MatchAdapter(teams) {
            startActivity<MatchDetailActivity>(MatchDetailActivity.INTENT_DETAIL to it)
        }
        listMatch = find(R.id.list_past_match)
        listMatch.adapter = matchAdapter
        presenter.getLeague()
    }

}