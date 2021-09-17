package com.dscunikom.android.footballmatchschedule.fragment.team

import com.dscunikom.android.footballmatchschedule.model.LeagueResponse
import com.dscunikom.android.footballmatchschedule.model.Team

interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueList(data: LeagueResponse)
    fun showTeamList(data: MutableList<Team>)
}