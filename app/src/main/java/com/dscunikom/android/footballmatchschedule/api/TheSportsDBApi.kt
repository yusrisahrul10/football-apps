package com.dscunikom.android.footballmatchschedule.api

import com.dscunikom.android.footballmatchschedule.BuildConfig

object TheSportsDBApi {

    fun getMatchPast(id: String?): String {
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/eventspastleague.php?id=${id}"
    }

    fun getMatchNext(id: String?): String {
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/eventsnextleague.php?id=${id}"
    }

    fun getTeam(id: String?): String {
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/lookupteam.php?id=${id}"
    }

    fun getTeamAll(leagueName: String): String {
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/search_all_teams.php?l=$leagueName"
    }

    fun getTeamSearch(teamName: String): String {
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/searchteams.php?t=$teamName"
    }

    fun getLeague(): String {
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/all_leagues.php"
    }

    fun getPlayer(teamName: String): String {
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/searchplayers.php?t=$teamName"
    }

    fun getMatchSearch(query: String): String {
        return "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}" +
                "/searchevents.php?e=$query"
    }

}