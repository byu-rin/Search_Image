@file:OptIn(ExperimentalCoroutinesApi::class)

package com.ai_curator

import android.content.Intent
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArtMovementActivityTest {

    // LiveData 를 즉시 실행하게 해주는 JUnit Rule
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // ✅ UI 접근 가능하게 UnconfinedTestDispatcher 사용
    private val testDispatcher = UnconfinedTestDispatcher()

    // 테스트 실행 전에 호출되는 메서드
    @Before
    fun setup() {
        // Main 디스패처를 테스트 디스패처로 대체 (테스트 제어 가능하게 하기 위함)
        Dispatchers.setMain(testDispatcher)
    }

    // 테스트가 끝난 후 디스패처를 원래 상태로 복구
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // 실제 테스트 메서드
    @Test
    fun clickOnArtistCard_updatesSelectedProfile() = runTest {
        // Arrange 단계 - 테스트할 Activity 실행
        val intent = Intent(ApplicationProvider.getApplicationContext(), ArtMovementActivity::class.java)
        val scenario = ActivityScenario.launch<ArtMovementActivity>(intent)

        // Act: 실제 ViewModel 에 접근해 직접 호출
        scenario.onActivity { activity ->
            val viewModel = activity.viewModel

            // 테스트할 아티스트 프로필 생성
            val profile = ArtProfile(
                imageResId = R.drawable.parksoyeong,
                name = "박소영"
            )

            // 실제 UI에서 클릭한 것처럼 ViewModel의 클릭 메서드 호출
            viewModel.onArtistProfileClicked(profile)

            assertEquals("박소영", viewModel.selectedProfile.value?.name)
            assertEquals(R.drawable.parksoyeong, viewModel.selectedProfile.value?.imageResId)
        }
    }
}

