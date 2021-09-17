package com.dscunikom.android.footballmatchschedule.activity.matchdetail

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.ProgressBar
import com.dscunikom.android.footballmatchschedule.R
import com.dscunikom.android.footballmatchschedule.R.drawable.ic_add_to_favorites
import com.dscunikom.android.footballmatchschedule.R.drawable.ic_added_to_favorites
import com.dscunikom.android.footballmatchschedule.R.id.add_to_favorite
import com.dscunikom.android.footballmatchschedule.R.menu.detail_menu
import com.dscunikom.android.footballmatchschedule.utils.invisible
import com.dscunikom.android.footballmatchschedule.utils.visible
import com.dscunikom.android.footballmatchschedule.api.ApiRepository
import com.dscunikom.android.footballmatchschedule.database.database
import com.dscunikom.android.footballmatchschedule.model.Match
import com.dscunikom.android.footballmatchschedule.model.Team
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_match_detail.*
import org.jetbrains.anko.db.*


class MatchDetailActivity : AppCompatActivity(), MatchDetailView {
    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamDetail(homeTeam: List<Team>, awayTeam: List<Team>) {
        val imgHome = findViewById<ImageView>(R.id.img_home)
        Picasso.get()
            .load(homeTeam[0].strTeamBadge)
            .into(imgHome)

        val imgAway = findViewById<ImageView>(R.id.img_away)
        Picasso.get()
            .load(awayTeam[0].strTeamBadge)
            .into(imgAway)
    }


    private lateinit var presenterMatch: MatchDetailPresenter
    private lateinit var data: Match
    private lateinit var progressBar: ProgressBar

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    companion object {
        const val INTENT_DETAIL = "EXTRA_DETAIL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)

        data = intent.getParcelableExtra(INTENT_DETAIL)

        progressBar = findViewById(R.id.progress_bar_detail)
        presenterMatch = MatchDetailPresenter(this, ApiRepository(), Gson())
        presenterMatch.getTeamDetail(data.idHomeTeam, data.idAwayTeam)
        initData(data)

        favoriteState()
    }

    fun initData(data: Match) {
        match_date.text = data.dateEvent
        home_score_match.text = data.intHomeScore
        away_score_match.text = data.intAwayScore

        home_name.text = data.strHomeTeam
        away_name.text = data.strAwayTeam

        home_shots.text = data.intHomeShots
        away_shot.text = data.intAwayShots

        home_goals.text = data.strHomeGoalDetails
        away_goals.text = data.strAwayGoalDetails

        home_goalkeeper.text = data.strHomeLineupGoalkeeper
        away_goalkeeper.text = data.strAwayLineupGoalkeeper

        home_defense.text = data.strHomeLineupDefense
        away_defense.text = data.strAwayLineupDefense

        home_midfield.text = data.strHomeLineupMidfield
        away_midfield.text = data.strAwayLineupMidfield

        home_forward.text = data.strHomeLineupForward
        away_forward.text = data.strAwayLineupForward

        home_substitutes.text = data.strHomeLineupSubstitutes
        away_substitutes.text = data.strAwayLineupSubstitutes
    }

    private fun favoriteState() {
        database.use {
            val result = select(Match.TABLE_FAVORITE)
                .whereArgs(
                    Match.ID_EVENT + " = {id}",
                    "id" to data.idEvent.toString()
                )
            val favorite = result.parseList(classParser<Match>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(
                    Match.TABLE_FAVORITE,
                    Match.DATE_EVENT to data.dateEvent,
                    Match.ID_AWAY_TEAM to data.idAwayTeam,
                    Match.ID_EVENT to data.idEvent,
                    Match.ID_HOME_TEAM to data.idHomeTeam,
                    Match.INT_AWAY_SCORE to data.intAwayScore,
                    Match.INT_HOME_SCORE to data.intHomeScore,
                    Match.STR_AWAY_GOAL_DETAILS to data.strAwayGoalDetails,
                    Match.INT_AWAY_SHOTS to data.intAwayShots,
                    Match.STR_AWAY_LINEUP_DEFENSE to data.strAwayLineupDefense,
                    Match.STR_AWAY_LINEUP_FORWARD to data.strAwayLineupForward,
                    Match.STR_AWAY_LINEUP_GOALKEEPER to data.strAwayLineupGoalkeeper,
                    Match.STR_AWAY_LINEUP_MIDFIELD to data.strAwayLineupMidfield,
                    Match.STR_AWAY_LINEUP_SUBSTITUTES to data.strAwayLineupSubstitutes,
                    Match.STR_AWAY_TEAM to data.strAwayTeam,
                    Match.STR_HOME_GOAL_DETAILS to data.strHomeGoalDetails,
                    Match.INT_HOME_SHOTS to data.intHomeShots,
                    Match.STR_HOME_LINEUP_DEFENSE to data.strHomeLineupDefense,
                    Match.STR_HOME_LINEUP_FORWARD to data.strHomeLineupForward,
                    Match.STR_HOME_LINEUP_GOALKEEPER to data.strHomeLineupGoalkeeper,
                    Match.STR_HOME_LINEUP_MIDFIELD to data.strHomeLineupMidfield,
                    Match.STR_HOME_LINEUP_SUBSTITUTES to data.strHomeLineupSubstitutes,
                    Match.STR_HOME_TEAM to data.strHomeTeam
                )
            }
            Log.e("Success", "Insert Success")
            Log.e("Succees,", data.strHomeTeam)
        } catch (e: SQLiteConstraintException) {
            Log.e("Failed", "Insert Failed")
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(
                    Match.TABLE_FAVORITE, Match.ID_EVENT + " = {id}",
                    "id" to data.idEvent.toString()
                )
            }
            Log.e("Success", "Delete Success")
            Log.e("Succees,", data.strHomeTeam)
        } catch (e: SQLiteConstraintException) {
            Log.e("Failed", "Delete Failed")
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }
}
