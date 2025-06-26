package com.ai_curator

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ai_curator.databinding.ActivityArtDetailBinding

class ArtDetailActivity : androidx.appcompat.app.AppCompatActivity() {
    private lateinit var binding: ActivityArtDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // image slider 용 viewpager2
        val imageItems: List<ArtWorkItem> = listOf(
            ImageItem(R.drawable.download_1),
            ImageItem(R.drawable.download_2),
            ImageItem(R.drawable.download_3),
            ImageItem(R.drawable.download_4)
        )
        val pagerAdapter = MultiTypeAdapter(imageItems, ViewType.IMAGE_SLIDER)
        binding.imagePager.adapter = pagerAdapter

        // RecyclerView (가로 스크롤) 세팅
        val otherWorks: List<ArtWorkItem> = listOf(
            ArtProfile(R.drawable.download_1, "작품 A"),
            ArtProfile(R.drawable.download_2, "작품 B"),
            ArtProfile(R.drawable.download_3, "작품 C")
        )
        val horizontalAdapter = MultiTypeAdapter(otherWorks, ViewType.ART_PROFILE)

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