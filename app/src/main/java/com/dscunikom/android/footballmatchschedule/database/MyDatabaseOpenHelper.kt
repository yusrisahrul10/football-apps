package com.dscunikom.android.footballmatchschedule.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.dscunikom.android.footballmatchschedule.model.Match
import com.dscunikom.android.footballmatchschedule.model.Team
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteMatch.db", null, 1) {

    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(
            Match.TABLE_FAVORITE, true,
            Match.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            Match.DATE_EVENT to TEXT,
            Match.ID_AWAY_TEAM to TEXT,
            Match.ID_EVENT to TEXT,
            Match.ID_HOME_TEAM to TEXT,
            Match.INT_AWAY_SCORE to TEXT,
            Match.INT_HOME_SCORE to TEXT,
            Match.STR_AWAY_GOAL_DETAILS to TEXT,
            Match.INT_AWAY_SHOTS to TEXT,
            Match.STR_AWAY_LINEUP_DEFENSE to TEXT,
            Match.STR_AWAY_LINEUP_FORWARD to TEXT,
            Match.STR_AWAY_LINEUP_GOALKEEPER to TEXT,
            Match.STR_AWAY_LINEUP_MIDFIELD to TEXT,
            Match.STR_AWAY_LINEUP_SUBSTITUTES to TEXT,
            Match.STR_AWAY_TEAM to TEXT,
            Match.STR_HOME_GOAL_DETAILS to TEXT,
            Match.INT_HOME_SHOTS to TEXT,
            Match.STR_HOME_LINEUP_DEFENSE to TEXT,
            Match.STR_HOME_LINEUP_FORWARD to TEXT,
            Match.STR_HOME_LINEUP_GOALKEEPER to TEXT,
            Match.STR_HOME_LINEUP_MIDFIELD to TEXT,
            Match.STR_HOME_LINEUP_SUBSTITUTES to TEXT,
            Match.STR_HOME_TEAM to TEXT
        )

        db.createTable(
            Team.TABLE_FAVORITE_TEAMS, true
            , Team.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            Team.ID_TEAM to TEXT,
            Team.STR_TEAM_BADGE to TEXT,
            Team.STR_TEAM to TEXT,
            Team.INT_FORMED_YEAR to TEXT,
            Team.STR_STADIUM to TEXT,
            Team.STR_DESCRIPTION_EN to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.dropTable(Match.TABLE_FAVORITE, true)
        db.dropTable(Team.TABLE_FAVORITE_TEAMS, true)
    }
}

val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)