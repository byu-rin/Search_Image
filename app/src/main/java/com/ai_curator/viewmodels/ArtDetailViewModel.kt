package com.ai_curator.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ai_curator.Artwork
import com.ai_curator.data.ArtworkRepository
import javax.inject.Inject

class ArtDetailViewModel @Inject constructor() : ViewModel() {
    private val allArtworks = ArtworkRepository.artworks

    private val _artworksByArtist = MutableLiveData<List<Artwork>>()
    val artworksByArtist: LiveData<List<Artwork>> = _artworksByArtist

    fun loadArtworksByArtistName(artistName: String) {
        val filtered = allArtworks.filter { it.artist == artistName }
        _artworksByArtist.value = filtered
    }
}