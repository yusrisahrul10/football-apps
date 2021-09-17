package com.dscunikom.android.footballmatchschedule.fragment.team

import com.dscunikom.android.footballmatchschedule.api.ApiRepository
import com.dscunikom.android.footballmatchschedule.api.TheSportsDBApi
import com.dscunikom.android.footballmatchschedule.model.LeagueResponse
import com.dscunikom.android.footballmatchschedule.model.TeamResponse
import com.dscunikom.android.footballmatchschedule.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class TeamPresenter(
    private val view: TeamView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getLeague() {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportsDBApi.getLeague()),
                    LeagueResponse::class.java
                )
            }
            view.hideLoading()
            view.showLeagueList(data.await())
        }
    }

    fun getTeam(leagueName: String) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportsDBApi.getTeamAll(leagueName))
                    , TeamResponse::class.java
                )
            }
            view.hideLoading()
            view.showTeamList(data.await().teams)
        }
    }

}