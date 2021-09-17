package com.dscunikom.android.footballmatchschedule.activity.teamdetail

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.DrawerActions
import android.support.test.espresso.contrib.DrawerMatchers
import android.support.test.espresso.contrib.NavigationViewActions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import com.dscunikom.android.footballmatchschedule.R
import com.dscunikom.android.footballmatchschedule.activity.main.MainActivity
import org.junit.Assert.*
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TeamDetailActivityTest {
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
    fun testTeamDetailBehaviour() {
        Thread.sleep(3000)
        Espresso.onView(ViewMatchers.withId(R.id.drawerLayout))
            .check(ViewAssertions.matches(DrawerMatchers.isClosed(Gravity.LEFT))).perform(DrawerActions.open())
        Espresso.onView(ViewMatchers.withId(R.id.navView)).perform(NavigationViewActions.navigateTo(R.id.nav_team))
        Thread.sleep(3000)
        Espresso.onView(ViewMatchers.withId(R.id.list_team)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.list_team)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        Espresso.onView(ViewMatchers.withId(R.id.list_team)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, ViewActions.click()))
        Thread.sleep(3000)

        Espresso.onView(ViewMatchers.withId(R.id.view_pager)).perform(ViewActions.swipeLeft())
        Thread.sleep(3000)
        Espresso.onView(ViewMatchers.withId(R.id.list_player))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.list_player)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        Espresso.onView(ViewMatchers.withId(R.id.list_player)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, ViewActions.click()))
        Thread.sleep(3000)
    }

    @Test
    fun testFavoriteTeamBehaviour() {
        Thread.sleep(3000)
        Espresso.onView(ViewMatchers.withId(R.id.drawerLayout))
            .check(ViewAssertions.matches(DrawerMatchers.isClosed(Gravity.LEFT))).perform(DrawerActions.open())
        Espresso.onView(ViewMatchers.withId(R.id.navView)).perform(NavigationViewActions.navigateTo(R.id.nav_team))
        Thread.sleep(3000)
        Espresso.onView(ViewMatchers.withId(R.id.list_team)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.list_team)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        Espresso.onView(ViewMatchers.withId(R.id.list_team)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, ViewActions.click()))
        Thread.sleep(3000)
        Espresso.onView(ViewMatchers.withId(R.id.add_to_favorite)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.add_to_favorite)).perform(ViewActions.click())
        Espresso.pressBack()
        Espresso.onView(ViewMatchers.withId(R.id.drawerLayout))
            .check(ViewAssertions.matches(DrawerMatchers.isClosed(Gravity.LEFT))).perform(DrawerActions.open())
        Espresso.onView(ViewMatchers.withId(R.id.navView)).perform(NavigationViewActions.navigateTo(R.id.nav_favorite))
        Thread.sleep(3000)
        Espresso.onView(ViewMatchers.withId(R.id.viewpager)).perform(ViewActions.swipeLeft())
        Espresso.onView(ViewMatchers.withId(R.id.list_team_favorite))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
