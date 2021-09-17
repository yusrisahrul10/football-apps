package com.dscunikom.android.footballmatchschedule.activity.search

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.dscunikom.android.footballmatchschedule.fragment.search.match.MatchSearchFragment
import com.dscunikom.android.footballmatchschedule.R
import com.dscunikom.android.footballmatchschedule.fragment.search.team.TeamSearchFragment
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        bottom_navigation_search.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bnv_match_search -> {
                    loadMatchSearchFragment(savedInstanceState)
                }
                R.id.bnv_teams_search -> {
                    loadTeamSearchFragment(savedInstanceState)
                }
            }
            true
        }
        bottom_navigation_search.selectedItemId = R.id.bnv_match_search
    }

    private fun loadMatchSearchFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.main_container_search,
                    MatchSearchFragment(), MatchSearchFragment::class.java.simpleName
                )
                .commit()
        }
    }

    private fun loadTeamSearchFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.main_container_search,
                    TeamSearchFragment(), TeamSearchFragment::class.java.simpleName
                )
                .commit()
        }
    }
}
