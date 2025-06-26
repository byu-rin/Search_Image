package com.ai_curator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.ai_curator.databinding.ActivityArtMovementBinding

class ArtMovementActivity : AppCompatActivity() {
    private val binding by lazy { ActivityArtMovementBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val artProfileList: List<ArtWorkItem> = listOf(
            ArtProfile(R.drawable.download_1, "한국화"),
            ArtProfile(R.drawable.download_2, "서양화"),
            ArtProfile(R.drawable.download_3, "서예"),
            ArtProfile(R.drawable.download_4, "시각디자인"),
            ArtProfile(R.drawable.download_1, "산업디자인"),
            ArtProfile(R.drawable.download_2, "금속디자인"),
            ArtProfile(R.drawable.download_3, "입체조형")
        )

        binding.movementGrid.adapter = MultiTypeAdapter(artProfileList, ViewType.ART_PROFILE)
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