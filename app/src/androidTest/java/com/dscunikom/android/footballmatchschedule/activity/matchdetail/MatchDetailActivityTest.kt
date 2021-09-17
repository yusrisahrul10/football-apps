package com.dscunikom.android.footballmatchschedule.activity.matchdetail

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
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
import org.junit.Before

import org.junit.Assert.*
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MatchDetailActivityTest {

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
   fun testMatchDetailBehaviour() {
       Thread.sleep(3000)
       Espresso.onView(ViewMatchers.withId(R.id.list_past_match))
           .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
       Espresso.onView(ViewMatchers.withId(R.id.list_past_match)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
       Espresso.onView(ViewMatchers.withId(R.id.list_past_match)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, ViewActions.click()))
       Thread.sleep(3000)
       pressBack()

       Espresso.onView(ViewMatchers.withId(R.id.viewpager)).perform(ViewActions.swipeLeft())
       Thread.sleep(3000)
       Espresso.onView(ViewMatchers.withId(R.id.list_next_match))
           .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
       Espresso.onView(ViewMatchers.withId(R.id.list_next_match)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
       Espresso.onView(ViewMatchers.withId(R.id.list_next_match)).perform(
           RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, ViewActions.click()))
       Thread.sleep(3000)
   }

   @Test
   fun testFavoriteMatchBehaviour() {
       Thread.sleep(3000)
       Espresso.onView(ViewMatchers.withId(R.id.list_past_match))
           .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
       Espresso.onView(ViewMatchers.withId(R.id.list_past_match)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
       Espresso.onView(ViewMatchers.withId(R.id.list_past_match)).perform(
           RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, ViewActions.click()))
       Thread.sleep(3000)
       Espresso.onView(ViewMatchers.withId(R.id.add_to_favorite)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
       Espresso.onView(ViewMatchers.withId(R.id.add_to_favorite)).perform(click())
        pressBack()

       Espresso.onView(ViewMatchers.withId(R.id.viewpager)).perform(ViewActions.swipeLeft())
       Thread.sleep(3000)
       Espresso.onView(ViewMatchers.withId(R.id.list_next_match))
           .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
       Espresso.onView(ViewMatchers.withId(R.id.list_next_match)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
       Espresso.onView(ViewMatchers.withId(R.id.list_next_match)).perform(
           RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, ViewActions.click()))
       Thread.sleep(3000)
       Espresso.onView(ViewMatchers.withId(R.id.add_to_favorite)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
       Espresso.onView(ViewMatchers.withId(R.id.add_to_favorite)).perform(click())
       pressBack()

       Espresso.onView(ViewMatchers.withId(R.id.drawerLayout))
           .check(ViewAssertions.matches(DrawerMatchers.isClosed(Gravity.LEFT))).perform(DrawerActions.open())
       Espresso.onView(ViewMatchers.withId(R.id.navView)).perform(NavigationViewActions.navigateTo(R.id.nav_favorite))
       Espresso.onView(ViewMatchers.withId(R.id.list_match_favorite))
           .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
   }
}