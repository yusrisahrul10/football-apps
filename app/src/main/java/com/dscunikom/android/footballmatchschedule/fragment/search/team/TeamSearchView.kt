package com.dscunikom.android.footballmatchschedule.fragment.search.team

import com.dscunikom.android.footballmatchschedule.model.Team

interface TeamSearchView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: MutableList<Team>)
}