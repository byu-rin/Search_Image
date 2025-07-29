package com.ai_curator.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ai_curator.Artwork
import com.ai_curator.data.ArtworkRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class DetailPageUiEvent {
    object ExpandText : DetailPageUiEvent()
}

class ArtDetailViewModel @Inject constructor() : ViewModel() {
    private val allArtworks = ArtworkRepository.artworks

    private val _artworksByArtist = MutableLiveData<List<Artwork>>()
    val artworksByArtist: LiveData<List<Artwork>> = _artworksByArtist

    fun loadArtworksByArtistName(artistName: String) {
        val filtered = allArtworks.filter { it.artist == artistName }
        _artworksByArtist.value = filtered
    }

    private val _eventChannel = Channel<DetailPageUiEvent>(Channel.BUFFERED)
    val eventFlow = _eventChannel.receiveAsFlow()

    val isExpanded = MutableStateFlow(false)
    fun onShowViewClicked() {
        viewModelScope.launch {
            if (!isExpanded.value) {
                _eventChannel.send(DetailPageUiEvent.ExpandText)
                isExpanded.value = true
            }
        }
    }
}