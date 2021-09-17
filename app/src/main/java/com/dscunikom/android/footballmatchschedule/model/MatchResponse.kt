package com.dscunikom.android.footballmatchschedule.model

import com.google.gson.annotations.SerializedName

data class MatchResponse(
    @SerializedName("events") val match: List<Match>
)