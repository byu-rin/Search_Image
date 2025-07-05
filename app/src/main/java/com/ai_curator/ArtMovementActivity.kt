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

        // RecyclerView 에 쓸 ArtProfile 데이터 생성
        val artProfileItems = ArtworkRepository.artworks
            .flatMap { artwork ->
                artwork.artistImage.map { imageResId ->
                    ArtProfile(imageResId, artwork.artist) } }

        val adapter = MultiTypeAdapter(artProfileItems, ViewType.ART_PROFILE)
        binding.movementGrid.adapter = adapter
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