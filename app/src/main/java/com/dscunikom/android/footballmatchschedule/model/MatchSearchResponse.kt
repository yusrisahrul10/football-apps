package com.dscunikom.android.footballmatchschedule.model

import com.google.gson.annotations.SerializedName

data class MatchSearchResponse(@SerializedName("event") val match : MutableList<Match>)