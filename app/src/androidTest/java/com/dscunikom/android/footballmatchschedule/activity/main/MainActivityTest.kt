package com.dscunikom.android.footballmatchschedule.activity.main

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.swipeLeft
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.DrawerActions
import android.support.test.espresso.contrib.DrawerMatchers.isClosed
import android.support.test.espresso.contrib.NavigationViewActions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import com.dscunikom.android.footballmatchschedule.R
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    companion object {
        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            InstrumentationRegistry.getTargetContext().deleteDatabase("FavoriteMatch.db")
        }
    }

    @Test
    fun testNavBarDrawerBehaviour() {
        Thread.sleep(3000)
        onView(withId(R.id.list_past_match)).check(matches(isDisplayed()))
        onView(withId(R.id.viewpager)).perform(swipeLeft())
        onView(withId(R.id.list_next_match)).check(matches(isDisplayed()))

        onView(withId(R.id.drawerLayout)).check(matches(isClosed(Gravity.LEFT))).perform(DrawerActions.open())
        onView(withId(R.id.navView)).perform(NavigationViewActions.navigateTo(R.id.nav_team))
        Thread.sleep(3000)
        onView(withId(R.id.list_team)).check(matches(isDisplayed()))

        onView(withId(R.id.drawerLayout)).check(matches(isClosed(Gravity.LEFT))).perform(DrawerActions.open())
        onView(withId(R.id.navView)).perform(NavigationViewActions.navigateTo(R.id.nav_favorite))
        onView(withId(R.id.list_match_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.viewpager)).perform(swipeLeft())
        onView(withId(R.id.list_team_favorite)).check(matches(isDisplayed()))


        onView(withId(R.id.drawerLayout)).check(matches(isClosed(Gravity.LEFT))).perform(DrawerActions.open())
        onView(withId(R.id.navView)).perform(NavigationViewActions.navigateTo(R.id.nav_match))
        Thread.sleep(3000)

        onView(withId(R.id.drawerLayout)).check(matches(isClosed(Gravity.LEFT))).perform(DrawerActions.open())
        onView(withId(R.id.navView)).perform(NavigationViewActions.navigateTo(R.id.nav_search))
        Thread.sleep(3000)
        onView(withId(R.id.rvMatch)).check(matches(isDisplayed()))
        onView(withId(R.id.bnv_teams_search)).perform(click())
        Thread.sleep(3000)
        onView(withId(R.id.rvTeam)).check(matches(isDisplayed()))
    }

}