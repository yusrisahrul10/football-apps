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

class NextMatchFragment : Fragment(), MainView, AnkoComponent<Context> {
    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            orientation = LinearLayout.VERTICAL

            relativeLayout {
                linearLayout {
                    backgroundResource = R.color.colorAccent
                    id = Ids.spinner_container
                    spinnerNext = spinner {
                        id = Ids.spinner_next
                    }.lparams(width = matchParent, height = wrapContent)
                }.lparams(width = matchParent, height = wrapContent)
                recyclerView {
                    id = R.id.list_next_match
                    layoutManager = LinearLayoutManager(ctx)
                }.lparams(width = matchParent, height = matchParent) {
                    below(Ids.spinner_container)
                }
                mainProgressBarNext = progressBar {
                    id = Ids.main_progress_bar_next
                }.lparams(width = wrapContent, height = wrapContent) {
                    centerInParent()
                }
            }.lparams(width = matchParent, height = wrapContent)
        }
    }

    private object Ids {
        val main_progress_bar_next = 1
        val spinner_next = 3
        val spinner_container = 4
    }

    override fun showLeagueList(data: LeagueResponse) {
        spinnerNext.adapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, data.leagues)

        spinnerNext.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                league = spinnerNext.selectedItem as League
                presenter.getMatchNext(league.idLeague.toString())
            }

        }
    }

    override fun showLoading() {
        mainProgressBarNext.visible()
    }

    override fun hideLoading() {
        mainProgressBarNext.invisible()
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
    private lateinit var spinnerNext: Spinner
    private lateinit var mainProgressBarNext: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = MainPresenter(this, ApiRepository(), Gson())
        matchAdapter = MatchAdapter(teams) {
            startActivity<MatchDetailActivity>(MatchDetailActivity.INTENT_DETAIL to it)
        }
        listMatch = find(R.id.list_next_match)
        listMatch.adapter = matchAdapter
        presenter.getLeague()
    }
}