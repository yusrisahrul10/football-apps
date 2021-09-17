package com.dscunikom.android.footballmatchschedule.fragment.search.team

import com.dscunikom.android.footballmatchschedule.api.ApiRepository
import com.dscunikom.android.footballmatchschedule.api.TheSportsDBApi
import com.dscunikom.android.footballmatchschedule.model.TeamResponse
import com.dscunikom.android.footballmatchschedule.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamSearchPresenter(
    private val view: TeamSearchView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getTeam(leagueName: String) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportsDBApi.getTeamAll(leagueName)),
                    TeamResponse::class.java
                )
            }
            view.hideLoading()
            try {
                view.showTeamList(data.await().teams)
            } catch (e: Exception) {
                view.hideLoading()
            }
        }
    }

    fun getTeamSearch(teamName: String) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportsDBApi.getTeamSearch(teamName)),
                    TeamResponse::class.java
                )
            }
            view.hideLoading()
            try {
                view.showTeamList(data.await().teams)
            } catch (e: Exception) {
                view.hideLoading()
            }
        }
    }
}