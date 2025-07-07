package com.ai_curator.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ai_curator.ArtProfile
import com.ai_curator.ArtWorkItem
import com.ai_curator.ArtistProfileState
import com.ai_curator.data.ArtworkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// 데이터 준비 및 상태관리 담당
class ArtMovementViewModel @Inject constructor() : ViewModel() {

    // 검색 여부
    private val _artMovementUiState = MutableStateFlow(ArtistProfileState())
    val artMovementUiState: StateFlow<ArtistProfileState> = _artMovementUiState.asStateFlow()

    // 프로필 클릭 여부
    private val _selectedProfile = MutableStateFlow<ArtProfile?>(null)
    val selectedProfile: StateFlow<ArtProfile?> = _selectedProfile.asStateFlow()

    // 검색 했으면 t, f
    fun fetchArtistProfile() {
        viewModelScope.launch {
            val profileItem = ArtworkRepository.artworks.flatMap { artwork ->
                artwork.artistImage.map { imageResId ->
                    ArtProfile(imageResId, artwork.artist)
                }
            }

            // 상태 업데이트 : 검색이 되었고 데이터가 존재함
            _artMovementUiState.value = _artMovementUiState.value.copy(
                isSearched = true,
                artProfileItems = profileItem
            )
        }
    }

    // 카드 클릭 시 해당 카드에 맞는 정보가 디테일페이지에 렌더링
    fun onArtistProfileClicked(artistProfile: ArtWorkItem) {
        val profile = artistProfile as? ArtProfile ?: return
        _selectedProfile.value = profile
    }

    // 작가 카드 클릭하면 디테일로 이동, 아니면 변화 없음
    // TODO: qr 클릭 시 카메라 실행
}