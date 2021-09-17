package com.dscunikom.android.footballmatchschedule.activity.teamdetail

import com.dscunikom.android.footballmatchschedule.model.Player

interface TeamPlayerView {

    fun showLoading()
    fun hideLoading()
    fun showPlayerList(data: MutableList<Player>)
}