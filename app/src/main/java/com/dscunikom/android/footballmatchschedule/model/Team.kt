package com.dscunikom.android.footballmatchschedule.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Team(
    val id: Long,
    @SerializedName("idTeam") var idTeam: String?,
    @SerializedName("strTeamBadge") var strTeamBadge: String?,
    @SerializedName("strTeam") var strTeam: String?,
    @SerializedName("intFormedYear") var intFormedYear: String?,
    @SerializedName("strStadium") var strStadium: String?,
    @SerializedName("strDescriptionEN") var strDescriptionEN: String?

) : Parcelable {
    companion object {
        const val TABLE_FAVORITE_TEAMS = "TABLE_FAVORITE_TEAMS"
        const val ID: String = "ID_"
        const val ID_TEAM = "ID_TEAM"
        const val STR_TEAM_BADGE = "STR_TEAM_BADGE"
        const val STR_TEAM = "STR_TEAM"
        const val INT_FORMED_YEAR = "INT_FORMED_YEAR"
        const val STR_STADIUM = "STR_STADIUM"
        const val STR_DESCRIPTION_EN = "STR_DESCRIPTION_EN"
    }
}