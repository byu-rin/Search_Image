@file:OptIn(ExperimentalCoroutinesApi::class)

package com.ai_curator

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
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

@RunWith(AndroidJUnit4::class)
class ArtDetailActivityTest {

    @Test
    fun artistId_isPassedCorrectly_viaIntent() {
        // 1. 테스트용 artistId 설정
        val testArtistId = "3"

        // 2. 테스트용 Intent 생성
        val intent = Intent(
            ApplicationProvider.getApplicationContext(),
            ArtDetailActivity::class.java
        ).apply {
            putExtra("artistId", testArtistId)
        }

        // 3. ActivityScenario 로 Activity 실행
        val scenario = ActivityScenario.launch<ArtDetailActivity>(intent)

        // 4. 결과 검증
        scenario.onActivity { activity ->
            val receivedId = activity.intent.getStringExtra("artistId")
            assertEquals("Intent 값이 다릅니다.", testArtistId, receivedId)
        }
    }
}

