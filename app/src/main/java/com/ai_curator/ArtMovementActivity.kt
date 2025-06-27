package com.ai_curator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.ai_curator.data.ArtworkRepository
import com.ai_curator.databinding.ActivityArtMovementBinding

class ArtMovementActivity : AppCompatActivity() {
    private val binding by lazy { ActivityArtMovementBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val artworks = ArtworkRepository.artworks

        val artProfileItems = artworks.map { ImageItem(it.artImage.first()) }
        val artProfileAdapter = MultiTypeAdapter(artProfileItems, ViewType.ART_PROFILE)
        binding.movementGrid.adapter = artProfileAdapter
        binding.movementGrid.layoutManager = GridLayoutManager(this, 2)
        binding.movementGrid.addItemDecoration(
            GridSpacingItemDecoration(
                spanCount = 2,
                spacing = 16,
                includeEdge = true
            )
        )
    }
}