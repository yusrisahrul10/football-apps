package com.dscunikom.android.footballmatchschedule.activity.matchdetail

import com.dscunikom.android.footballmatchschedule.model.Team

interface MatchDetailView {
    fun showLoading()
    fun hideLoading()
    fun showTeamDetail(homeTeam: List<Team>, awayTeam: List<Team>)
}