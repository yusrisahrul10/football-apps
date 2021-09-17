package com.dscunikom.android.footballmatchschedule.fragment.search.match

import com.dscunikom.android.footballmatchschedule.api.ApiRepository
import com.dscunikom.android.footballmatchschedule.api.TheSportsDBApi
import com.dscunikom.android.footballmatchschedule.model.MatchSearchResponse
import com.dscunikom.android.footballmatchschedule.utils.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchSearchPresenter(
    private val view: MatchSearchView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getMatchSearch(query: String = "") {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportsDBApi.getMatchSearch(query)),
                    MatchSearchResponse::class.java
                )
            }
            view.hideLoading()
            try {
                view.showMatchList(data.await().match)

            } catch (e: Exception) {
                view.hideLoading()
            }
        }
    }
}