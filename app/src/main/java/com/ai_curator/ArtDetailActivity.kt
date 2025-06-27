package com.ai_curator

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ai_curator.data.ArtworkRepository
import com.ai_curator.databinding.ActivityArtDetailBinding

class ArtDetailActivity : androidx.appcompat.app.AppCompatActivity() {
    private lateinit var binding: ActivityArtDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val artworks = ArtworkRepository.artworks

        // image slider 용 viewpager2
        val pagerItems = artworks.map { ImageItem(it.artImage.first()) }
        val pagerAdapter = MultiTypeAdapter(pagerItems, ViewType.IMAGE_SLIDER)
        binding.imagePager.adapter = pagerAdapter

        // RecyclerView (가로 스크롤) 세팅
        val otherWorksItems = artworks.map { ArtProfile(it.artImage.first(), null) }
        val horizontalAdapter = MultiTypeAdapter(otherWorksItems, ViewType.ART_PROFILE)
        binding.otherArtworksRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.otherArtworksRecyclerView.adapter = horizontalAdapter
        binding.otherArtworksRecyclerView.addItemDecoration(HorizontalSpacingItemDecoration(24))
        binding.otherArtworksRecyclerView.isNestedScrollingEnabled = false
    }

    class HorizontalSpacingItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            outRect.right = space
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.left = space
            }
        }
    }
}