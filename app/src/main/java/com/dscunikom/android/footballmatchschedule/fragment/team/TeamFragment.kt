package com.dscunikom.android.footballmatchschedule.fragment.team

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.*
import com.dscunikom.android.footballmatchschedule.R
import com.dscunikom.android.footballmatchschedule.activity.teamdetail.TeamDetailActivity
import com.dscunikom.android.footballmatchschedule.adapter.TeamAdapter
import com.dscunikom.android.footballmatchschedule.api.ApiRepository
import com.dscunikom.android.footballmatchschedule.model.League
import com.dscunikom.android.footballmatchschedule.model.LeagueResponse
import com.dscunikom.android.footballmatchschedule.model.Team
import com.dscunikom.android.footballmatchschedule.utils.invisible
import com.dscunikom.android.footballmatchschedule.utils.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.startActivity

class TeamFragment : Fragment(), TeamView, AnkoComponent<Context> {
    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            orientation = LinearLayout.VERTICAL

            relativeLayout {
                linearLayout {
                    backgroundResource = R.color.colorAccent
                    id = Ids.spinner_container_team

                    spinnerTeam = spinner {
                        id = Ids.spinner_team
                    }.lparams(width = matchParent, height = wrapContent)
                }.lparams(width = matchParent, height = wrapContent)
                recyclerView {
                    id = R.id.list_team
                    layoutManager = LinearLayoutManager(ctx)
                }.lparams(width = matchParent, height = matchParent) {
                    below(Ids.spinner_container_team)
                }
                mainProgressBarTeam = progressBar {
                    id = Ids.main_progress_bar_Team
                }.lparams(width = wrapContent, height = wrapContent) {
                    centerInParent()
                }
            }.lparams(width = matchParent, height = wrapContent)
        }
    }

    private object Ids {
        val main_progress_bar_Team = 1
        val spinner_team = 3
        val spinner_container_team = 4
    }

    override fun showLoading() {
        mainProgressBarTeam.visible()
    }

    override fun hideLoading() {
        mainProgressBarTeam.invisible()
    }

    override fun showLeagueList(data: LeagueResponse) {
        spinnerTeam.adapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, data.leagues)
        spinnerTeam.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                league = spinnerTeam.selectedItem as League
                leagueUrl = league.strLeague.toString()
                leagueUrl = leagueUrl.replace(" ", "%20")

                presenter.getTeam(leagueUrl)
                Log.e("Dengan Spasi", league.strLeague.toString())
                Log.e("Tanpa Spasi", leagueUrl)
            }
        }
    }


    override fun showTeamList(data: MutableList<Team>) {
        teams.clear()
        teams.addAll(data)
        teamAdapter.notifyDataSetChanged()
    }

    private lateinit var presenter: TeamPresenter
    private lateinit var league: League
    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var teamAdapter: TeamAdapter
    private lateinit var spinnerTeam: Spinner
    private lateinit var mainProgressBarTeam: ProgressBar
    private lateinit var listTeam: RecyclerView
    private lateinit var leagueUrl : String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter =
                TeamPresenter(this, ApiRepository(), Gson())

        teamAdapter = TeamAdapter(teams) {
            startActivity<TeamDetailActivity>(TeamDetailActivity.INTENT_DETAIL to it)
        }
        listTeam = find(R.id.list_team)
        listTeam.adapter = teamAdapter
        presenter.getLeague()
    }
}