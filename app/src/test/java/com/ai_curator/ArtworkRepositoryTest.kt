package com.ai_curator

import com.ai_curator.data.ArtworkRepository
import org.junit.Assert.*
import org.junit.Test

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



