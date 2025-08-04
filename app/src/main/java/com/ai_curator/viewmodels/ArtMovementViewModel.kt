package com.ai_curator.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ai_curator.ArtistProfile
import com.ai_curator.data.ArtistRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MovementPageUiEvent {
    data class NavigateToDetail(val artistProfile: ArtistProfile) : MovementPageUiEvent()
}

// 데이터 준비, 상태관리, 비즈니스 로직
class ArtMovementViewModel @Inject constructor() : ViewModel() {
    private val _artistProfileUiState = MutableLiveData<List<ArtistProfile>>()
    val artistProfileUiState: LiveData<List<ArtistProfile>> get() = _artistProfileUiState // 읽기 전용

    private var fullArtistList: List<ArtistProfile> = emptyList()

    // onCreate 또는 필요 시점에 호출. 작가 리스트 로드.
    // 내부적으로 상태 업데이트. 리턴값으로 artists 리스트 즉시 전달
    fun loadArtistProfile() {
        fullArtistList = ArtistRepository.artists
        _artistProfileUiState.value = fullArtistList
        Log.d("SearchDebug", "Loaded artist list: ${fullArtistList.size}")
//        viewModelScope.launch {
//            // Repository 로부터 작가 데이터 가져와 StateFlow 업데이트 (비동기 처리)
//            _artistProfileUiState.value = ArtistRepository.artists
//        }
//        // 실제 UI 에 표시할 데이터 반환 (launch 블록과 별개로 즉시 실행)
//        return ArtistRepository.artists
    }

    // 특정 작가 카드 클릭 시 이벤트를 Channel로 전송
    // Activity에서 collect하여 Navigation 처리
//    fun onArtistProfileClicked(artistProfile: ArtistProfile) {
//        viewModelScope.launch {
//            _eventChannel.send(MovementPageUiEvent.NavigateToDetail(artistProfile))
//        }
//    }

    private val _eventFlow = Channel<MovementPageUiEvent>(Channel.BUFFERED)
    val eventFlow = _eventFlow.receiveAsFlow()

    // 검색어에 따라 작가 리스트를 필터링하여 UI 상태 업데이트
    // 검색어가 비어있으면 전체 리스트를 보여줌
    fun onSearchQueryChanged(query: String) {
        val trimmedQuery = query.trim()
        val filtered = if (query.isBlank()) {
            fullArtistList
        } else {
            fullArtistList.filter {
                it.artistName.contains(trimmedQuery, ignoreCase = true)
            }
        }
        Log.d("ArtMovementViewModel", "Search Query: '$trimmedQuery' → ${filtered.size} result(s)")
        _artistProfileUiState.value = filtered
    }

    fun onArtistProfileClicked(artistProfile: ArtistProfile) {
        viewModelScope.launch {
            // Repository 로부터 작가 데이터 가져와 StateFlow 업데이트 (비동기 처리)
           _eventFlow.send(MovementPageUiEvent.NavigateToDetail(artistProfile))
        }
    }

    // test 용
    fun loadTestArtists(artists: List<ArtistProfile>) {
        fullArtistList = artists
        _artistProfileUiState.value = artists
    }
}