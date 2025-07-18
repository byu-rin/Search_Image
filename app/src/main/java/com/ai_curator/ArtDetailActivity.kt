package com.ai_curator

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.ai_curator.data.ArtworkRepository
import com.ai_curator.databinding.ActivityArtDetailBinding

class ArtDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArtDetailBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var pagerAdapter: ArtworkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = binding.imagePager
        pagerAdapter = ArtworkAdapter(ArtworkRepository.artworks)
        viewPager.adapter = pagerAdapter

        val container = binding.otherArtworksLinearLayout // 연관 작품
        val artworks = ArtworkRepository.artworks.take(3) // 3개의 작품만 가져옴

        // 가로형 스크롤 otherartworks
        artworks.forEach { artwork ->
            val imageView = ImageView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    resources.getDimensionPixelSize(R.dimen.art_item_width),
                    resources.getDimensionPixelSize(R.dimen.art_item_height)
                ).apply {
                    setMargins(0, 0, 24, 0) // 오른쪽 여백
                }
                scaleType = ImageView.ScaleType.CENTER_CROP
                setImageResource(artwork.artImageRes)
                contentDescription = "Artwork"
                background = ContextCompat.getDrawable(context, R.drawable.cardview_all_round)
            }
            container.addView(imageView)
        }
    }
}