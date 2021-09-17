package com.dscunikom.android.footballmatchschedule.activity.matchdetail

import com.dscunikom.android.footballmatchschedule.utils.CoroutineContextProvider
import com.dscunikom.android.footballmatchschedule.api.ApiRepository
import com.dscunikom.android.footballmatchschedule.api.TheSportsDBApi
import com.dscunikom.android.footballmatchschedule.model.TeamResponse
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class MatchDetailPresenter(
    private val viewMatch: MatchDetailView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getTeamDetail(idHomeTeam: String?, idAwayTeam: String?) {

        viewMatch.showLoading()

        async(context.main) {
            val dataHome = bg {
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportsDBApi.getTeam(idHomeTeam.toString())),
                    TeamResponse::class.java
                )
            }
            val dataAway = bg {
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportsDBApi.getTeam(idAwayTeam.toString())), TeamResponse::class.java
                )
            }
            viewMatch.hideLoading()
            viewMatch.showTeamDetail(dataHome.await().teams, dataAway.await().teams)

        }
    }
}

