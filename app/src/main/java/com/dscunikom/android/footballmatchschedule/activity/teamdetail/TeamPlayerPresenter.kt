package com.dscunikom.android.footballmatchschedule.activity.teamdetail

import com.dscunikom.android.footballmatchschedule.api.ApiRepository
import com.dscunikom.android.footballmatchschedule.api.TheSportsDBApi
import com.dscunikom.android.footballmatchschedule.model.PlayerResponse
import com.dscunikom.android.footballmatchschedule.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class TeamPlayerPresenter(
    private val view: TeamPlayerView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getPlayer(teamName: String) {
        view.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportsDBApi.getPlayer(teamName)),
                    PlayerResponse::class.java
                )
            }
            view.hideLoading()
            view.showPlayerList(data.await().player)
        }
    }

}