package com.dscunikom.android.footballmatchschedule.activity.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.View
import com.dscunikom.android.footballmatchschedule.R
import com.dscunikom.android.footballmatchschedule.fragment.favorite.TabFavoriteFragment
import com.dscunikom.android.footballmatchschedule.fragment.match.TabMatchFragment
import com.dscunikom.android.footballmatchschedule.fragment.team.TeamFragment
import com.dscunikom.android.footballmatchschedule.activity.search.SearchActivity
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mNavigationView: NavigationView
    private lateinit var mFragmentManager: FragmentManager
    private lateinit var mfragmentTransaction: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDrawerLayout = find(R.id.drawerLayout)
        mNavigationView = find(R.id.navView)

        mFragmentManager = supportFragmentManager
        mfragmentTransaction = mFragmentManager.beginTransaction()
        mfragmentTransaction.replace(
            R.id.containerView,
            TabMatchFragment()
        ).commit()

        mNavigationView.setNavigationItemSelectedListener { menuItem ->
            mDrawerLayout.closeDrawers()

            if (menuItem.itemId == R.id.nav_match) {
                val ft = mFragmentManager.beginTransaction()
                ft.replace(
                    R.id.containerView,
                    TabMatchFragment()
                ).commit()
            }
            if (menuItem.itemId == R.id.nav_team) {
                val ft = mFragmentManager.beginTransaction()
                ft.replace(
                    R.id.containerView,
                    TeamFragment()
                ).commit()
            }
            if (menuItem.itemId == R.id.nav_favorite) {
                val ft = mFragmentManager.beginTransaction()
                ft.replace(
                    R.id.containerView,
                    TabFavoriteFragment()
                ).commit()
            }
            if (menuItem.itemId == R.id.nav_search) {
                startActivity<SearchActivity>()
            }

            false
        }
        val toolbar = findViewById<View>(R.id.toolbar_main) as Toolbar
        val mDrawerToggle = ActionBarDrawerToggle(
            this, mDrawerLayout, toolbar,
            R.string.app_name,
            R.string.app_name
        )
        mDrawerLayout.setDrawerListener(mDrawerToggle)
        mDrawerToggle.syncState()
    }


}
