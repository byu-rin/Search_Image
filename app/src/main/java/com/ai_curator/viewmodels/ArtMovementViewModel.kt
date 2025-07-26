package com.ai_curator.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ai_curator.ArtistProfile
import com.ai_curator.data.ArtistRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// 데이터 준비, 상태관리, 비즈니스 로직
class ArtMovementViewModel @Inject constructor() : ViewModel() {
    // 작가 프로필 UI 상태 - 빈 리스트로 초기화
    // View에서 작가 목록을 관찰하고, 변경 시 UI 자동 업데이트
    private val _artistProfileUiState = MutableStateFlow<List<ArtistProfile>>(emptyList())
    val artistProfileUiState: StateFlow<List<ArtistProfile>> = _artistProfileUiState.asStateFlow() // 읽기 전용

    // onCreate 또는 필요 시점에 호출. 작가 리스트 로드.
    // 내부적으로 상태 업데이트. 리턴값으로 artists 리스트 즉시 전달
    fun loadArtistProfile() : List<ArtistProfile> {
        viewModelScope.launch {
            // Repository 로부터 작가 데이터 가져와 StateFlow 업데이트 (비동기 처리)
            _artistProfileUiState.value = ArtistRepository.artists
        }
        // 실제 UI 에 표시할 데이터 반환 (launch 블록과 별개로 즉시 실행)
        return ArtistRepository.artists
    }
}