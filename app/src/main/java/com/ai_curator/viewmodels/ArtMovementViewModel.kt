package com.ai_curator.viewmodels

import android.util.Log
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

    // 초기화
    // ViewModel 내 상태를 리스트로 보관해야 전체를 다룰 수 있음
    private val _artistProfileUiState = MutableStateFlow<List<ArtistProfile>>(emptyList())

    // 작가 프로필 관리
//    private val _artistProfileUiState = MutableStateFlow(ArtistProfile()) // 수정도 가능. 객체 생성하고 초기값 바로 넣기, 절대 null 불허
    val artistProfileUiState: StateFlow<List<ArtistProfile>> = _artistProfileUiState.asStateFlow() // 읽기 전용

    // onCreate 시 호출
    fun loadArtistProfile() : List<ArtistProfile> {
        viewModelScope.launch {
            _artistProfileUiState.value = ArtistRepository.artists
        }
        return ArtistRepository.artists
    }

    // 작가 프로필 클릭
    private val _selectedProfile = MutableStateFlow<ArtistProfile?> (null)
    val selectedProfile: StateFlow<ArtistProfile?> = _selectedProfile.asStateFlow()

    // 검색 했으면 t, f
//    fun fetchArtistProfile() {
//        viewModelScope.launch {
//            val profileItem = ArtworkRepository.artworks.flatMap { artwork ->
//                artwork.artistImage.map { imageResId ->
//                    ArtistProfile(artwork.id, imageResId, artwork.artist)
//                }
//            }
//
//            // 상태 업데이트 : 검색이 되었고 데이터가 존재함
//            _artMovementUiState.value = _artMovementUiState.value.copy(
//                isSearched = true,
//                artProfileItems = profileItem
//            )
//        }
//    }

    // 카드 클릭 시 해당 카드에 맞는 정보가 디테일페이지에 렌더링
    fun onArtistProfileClicked(artistProfile: ArtistProfile) {
        val profile = artistProfile
        _selectedProfile.value = profile

        // artistId 로그 찍기
        Log.d("ClickedArtist", "선택된 작가 ID: $_selectedProfile.value?.artistId")
    }

    // 작가 카드 클릭하면 디테일로 이동, 아니면 변화 없음
    // TODO: qr 클릭 시 카메라 실행
}