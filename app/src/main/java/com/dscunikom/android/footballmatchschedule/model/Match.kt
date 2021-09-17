package com.dscunikom.android.footballmatchschedule.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Match(
    val id: Long?,
    @SerializedName("dateEvent") var dateEvent: String?,
    @SerializedName("idAwayTeam") var idAwayTeam: String,
    @SerializedName("idEvent") var idEvent: String?,
    @SerializedName("idHomeTeam") var idHomeTeam: String,
    @SerializedName("intAwayScore") var intAwayScore: String?,
    @SerializedName("intHomeScore") var intHomeScore: String?,
    @SerializedName("strAwayGoalDetails") var strAwayGoalDetails: String?,
    @SerializedName("intAwayShots") var intAwayShots: String?,
    @SerializedName("strAwayLineupDefense") var strAwayLineupDefense: String?,
    @SerializedName("strAwayLineupForward") var strAwayLineupForward: String?,
    @SerializedName("strAwayLineupGoalkeeper") var strAwayLineupGoalkeeper: String?,
    @SerializedName("strAwayLineupMidfield") var strAwayLineupMidfield: String?,
    @SerializedName("strAwayLineupSubstitutes") var strAwayLineupSubstitutes: String?,
    @SerializedName("strAwayTeam") var strAwayTeam: String?,
    @SerializedName("strHomeGoalDetails") var strHomeGoalDetails: String?,
    @SerializedName("intHomeShots") var intHomeShots: String?,
    @SerializedName("strHomeLineupDefense") var strHomeLineupDefense: String?,
    @SerializedName("strHomeLineupForward") var strHomeLineupForward: String?,
    @SerializedName("strHomeLineupGoalkeeper") var strHomeLineupGoalkeeper: String?,
    @SerializedName("strHomeLineupMidfield") var strHomeLineupMidfield: String?,
    @SerializedName("strHomeLineupSubstitutes") var strHomeLineupSubstitutes: String?,
    @SerializedName("strHomeTeam") var strHomeTeam: String?

) : Parcelable {
    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val DATE_EVENT: String = "DATE_EVENT"
        const val ID_AWAY_TEAM: String = "ID_AWAY_TEAM"
        const val ID_EVENT: String = "ID_EVENT"
        const val ID_HOME_TEAM: String = "ID_HOME_TEAM"
        const val INT_AWAY_SCORE: String = "INT_AWAY_SCORE"
        const val INT_HOME_SCORE: String = "ID_HOME_SCORE"
        const val STR_AWAY_GOAL_DETAILS: String = "STR_AWAY_GOAL_DETAILS"
        const val INT_AWAY_SHOTS: String = "INT_AWAY_SHOTS"
        const val STR_AWAY_LINEUP_DEFENSE: String = "STR_AWAY_LINEUP_DEFENSE"
        const val STR_AWAY_LINEUP_FORWARD: String = "STR_AWAY_LINEUP_FORWARD"
        const val STR_AWAY_LINEUP_GOALKEEPER: String = "STR_AWAY_LINEUP_GOALKEEPER"
        const val STR_AWAY_LINEUP_MIDFIELD: String = "STR_AWAY_LINEUP_MIDFIELD"
        const val STR_AWAY_LINEUP_SUBSTITUTES: String = "STR_AWAY_LINEUP_SUBSTITUTES"
        const val STR_AWAY_TEAM: String = "STR_AWAY_TEAM"
        const val STR_HOME_GOAL_DETAILS: String = "STR_HOME_GOAL_DETAILS"
        const val INT_HOME_SHOTS: String = "INT_HOME_SHOTS"
        const val STR_HOME_LINEUP_DEFENSE: String = "STR_HOME_LINEUP_DEFENSE"
        const val STR_HOME_LINEUP_FORWARD: String = "STR_HOME_LINEUP_FORWARD"
        const val STR_HOME_LINEUP_GOALKEEPER: String = "STR_HOME_LINEUP_GOALKEEPER"
        const val STR_HOME_LINEUP_MIDFIELD: String = "STR_HOME_LINEUP_MIDFIELD"
        const val STR_HOME_LINEUP_SUBSTITUTES: String = "STR_HOME_LINEUP_SUBSTITUTES"
        const val STR_HOME_TEAM: String = "STR_HOME_TEAM"
    }
}
