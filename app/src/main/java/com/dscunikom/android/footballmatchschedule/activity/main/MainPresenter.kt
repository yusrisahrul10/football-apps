package com.dscunikom.android.footballmatchschedule.activity.main

import com.dscunikom.android.footballmatchschedule.utils.CoroutineContextProvider
import com.dscunikom.android.footballmatchschedule.api.ApiRepository
import com.dscunikom.android.footballmatchschedule.api.TheSportsDBApi
import com.dscunikom.android.footballmatchschedule.model.LeagueResponse
import com.dscunikom.android.footballmatchschedule.model.MatchResponse
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class MainPresenter(
    private val view: MainView,
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

    fun getMatchPast(id: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportsDBApi.getMatchPast(id)),
                    MatchResponse::class.java
                )
            }
            view.hideLoading()
            view.showMatchList(data.await().match)
        }

    }

    fun getMatchNext(id: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportsDBApi.getMatchNext(id)),
                    MatchResponse::class.java
                )
            }
            view.hideLoading()
            view.showMatchList(data.await().match)

        }
    }

}



