package com.ai_curator

import com.ai_curator.data.ArtworkRepository
import com.ai_curator.viewmodels.ArtMovementViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class ArtworkRepositoryTest {

    @Test
    fun findArtworkById_shouldReturnCorrectArtwork() {
        val targetId = "2"
        val artwork = ArtworkRepository.artworks.find { it.artworkId == targetId }

        assertNotNull("Artwork should not be null", artwork)
        assertEquals("이은지", artwork?.artist)
        assertEquals("alice wake up", artwork?.title)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {

    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class ArtMovementViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: ArtMovementViewModel

    private val testArtists = listOf(
        ArtistProfile("1", 0, "박소영"),
        ArtistProfile("2", 0, "이은지"),
        ArtistProfile("3", 0, "진선아"),
        ArtistProfile("4", 0, "정유경"),
        ArtistProfile("5", 0, "좌현진"),
        ArtistProfile("6", 0, "김지희"),
        ArtistProfile("7", 0, "진형현"),
        ArtistProfile("8", 0, "최지훈"),
    )

    @Before
    fun setUp() {
        viewModel = ArtMovementViewModel()
        // 강제로 ArtistRepository.artists 대체
        viewModel.loadTestArtists(testArtists)
    }

    @Test
    fun `검색어가 공백일 경우 전체 목록 반환`() {
        viewModel.onSearchQueryChanged("  ")

        val result = viewModel.artistProfileUiState.getOrAwaitValue()
        assertEquals(8, result.size)
    }

    @Test
    fun `정확한 이름 검색 시 1개 결과 반환`() {
        viewModel.onSearchQueryChanged("박소영")

        val result = viewModel.artistProfileUiState.getOrAwaitValue()
        assertEquals(1, result.size)
        assertEquals("박소영", result[0].artistName)
    }

    @Test
    fun `일부 이름 검색 시 여러 결과 반환`() {
        viewModel.onSearchQueryChanged("진")

        val result = viewModel.artistProfileUiState.getOrAwaitValue()
        assertTrue(result.size >= 2)
        assertTrue(result.any { it.artistName.contains("진") })
    }

    @Test
    fun `검색 결과가 없는 경우 빈 리스트 반환`() {
        viewModel.onSearchQueryChanged("없는사람")

        val result = viewModel.artistProfileUiState.getOrAwaitValue()
        assertEquals(0, result.size)
    }
}