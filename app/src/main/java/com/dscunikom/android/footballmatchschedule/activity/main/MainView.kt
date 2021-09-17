package com.dscunikom.android.footballmatchschedule.activity.main

import com.dscunikom.android.footballmatchschedule.model.LeagueResponse
import com.dscunikom.android.footballmatchschedule.model.Match

interface MainView {
    fun showLoading()
    fun hideLoading()
    fun showMatchList(data: List<Match>)
    fun showLeagueList(data: LeagueResponse)
}