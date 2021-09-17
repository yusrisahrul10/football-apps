package com.dscunikom.android.footballmatchschedule.fragment.search.match

import com.dscunikom.android.footballmatchschedule.model.Match

interface MatchSearchView {
    fun showLoading()
    fun hideLoading()
    fun showMatchList(data: MutableList<Match>)
}