@file:OptIn(ExperimentalCoroutinesApi::class)

package com.ai_curator

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArtMovementActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(ArtMovementActivity::class.java)

    @Test
    fun recyclerView_isDisplayed_onActivityLaunch() {
        onView(withId(R.id.movementGrid)) // movementGrid가 xml에 지정된 id
            .check(matches(isDisplayed()))
            .check(matches(hasMinimumChildCount(1)))
    }
}

