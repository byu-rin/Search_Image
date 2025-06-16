package com.ai_curator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.ai_curator.data.ArtProfile
import com.ai_curator.data.CategoryItemAdapter
import com.ai_curator.databinding.ActivityArtMovementBinding

class ArtMovementActivity : AppCompatActivity() {
    private val binding by lazy { ActivityArtMovementBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val artProfileList = ArrayList<ArtProfile>()
        artProfileList.add(ArtProfile(R.drawable.download_1, "한국화"))
        artProfileList.add(ArtProfile(R.drawable.download_2, "서양화"))
        artProfileList.add(ArtProfile(R.drawable.download_3, "서예"))
        artProfileList.add(ArtProfile(R.drawable.download_4, "시각디자인"))
        artProfileList.add(ArtProfile(R.drawable.download_1, "산업디자인"))
        artProfileList.add(ArtProfile(R.drawable.download_2, "금속디자인"))
        artProfileList.add(ArtProfile(R.drawable.download_3, "입체조형"))

        binding.movementGrid.adapter = CategoryItemAdapter(artProfileList)
        binding.movementGrid.layoutManager = GridLayoutManager(this, 2)
        binding.movementGrid.addItemDecoration(
            GridSpacingItemDecoration(
                spanCount = 2,
                spacing = 16,
                includeEdge = true
            )
        )

//        binding.movementGrid.run {
//            adapter = CategoryItemAdapter(artProfileList)
//            val spanCount = 2
//            val space = 20
//            addItemDecoration(GridSpaceCategoryItemDecoration(spanCount, space))
//        }
    }
}