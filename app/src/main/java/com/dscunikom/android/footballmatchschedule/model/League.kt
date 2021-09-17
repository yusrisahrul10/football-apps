package com.dscunikom.android.footballmatchschedule.model

import com.google.gson.annotations.SerializedName

data class League(
    @SerializedName("idLeague")
    var idLeague: String?,
    @SerializedName("strLeague")
    val strLeague: String?
) {

    override fun toString(): String {
        return strLeague.toString()
    }
}