package com.example.video_solution


import android.view.View
import android.view.ViewGroup

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.internal.matcher.DrawableMatcher.Companion.withDrawable
import org.hamcrest.CoreMatchers.allOf

import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class PlaylistFeature {

    val mActivityRule = ActivityTestRule(MainActivity::class.java)

    @Rule get


    @Test
    fun displayScreenTitle() {

        assertDisplayed("Playlists")
    }

    @Test
    fun displaysListOfPlaylists(){

        Thread.sleep(4000)



        assertRecyclerViewItemCount(R.id.playlists_list, 10)

        //user expresso para el test de vistas ?? allof de a id de color de difetentes valores
        onView(allOf(withId(R.id.playlist_name), isDescendantOfA(nthChildOf(withId(R.id.playlists_list),0))))
            .check(matches(withText("Hard Rock Cafe")))
            .check(matches(isDisplayed()))

        onView(allOf(withId(R.id.playlist_category), isDescendantOfA(nthChildOf(withId(R.id.playlists_list),0))))
            .check(matches(withText("rock")))
            .check(matches(isDisplayed()))

        onView(allOf(withId(R.id.playlist_image), isDescendantOfA(nthChildOf(withId(R.id.playlists_list),0))))
            .check(matches(withDrawable(R.mipmap.playlist)))
            .check(matches(isDisplayed()))
    }



    fun nthChildOf(parentMatcher: org.hamcrest.Matcher<View>, childPosition: Int):org.hamcrest.Matcher<View>{

        return object : TypeSafeMatcher<View>(){
            override fun describeTo(description: Description?) {
                description!!.appendText("position $childPosition of parent ")
                parentMatcher.describeTo(description)
            }

            override fun matchesSafely(item: View?): Boolean {
                if (item!!.parent !is ViewGroup) return false
                val parent = item.parent as ViewGroup

                //compara las posiciones
                return (parentMatcher.matches(parent)
                        && parent.childCount>childPosition
                        && parent.getChildAt(childPosition)==item)
            }

        }

    }

}