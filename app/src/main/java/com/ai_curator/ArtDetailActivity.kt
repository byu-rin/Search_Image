package com.ai_curator

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.ai_curator.data.ArtworkRepository
import com.ai_curator.databinding.ActivityArtDetailBinding

class ArtDetailActivity : androidx.appcompat.app.AppCompatActivity() {
    private lateinit var binding: ActivityArtDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val container = binding.otherArtworksLinearLayout
        val artworks = ArtworkRepository.artworks.take(3)

        // image slider 용 viewpager2
        val pagerItems = artworks.map { ImageItem(it.artImage.first()) }
        val pagerAdapter = MultiTypeAdapter(pagerItems, ViewType.IMAGE_SLIDER, onClick = {})
        binding.imagePager.adapter = pagerAdapter

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
                setImageResource(artwork.artImage.first())
                contentDescription = "Artwork"
                background = ContextCompat.getDrawable(context, R.drawable.cardview_all_round)
            }
            container.addView(imageView)
        }
    }
}