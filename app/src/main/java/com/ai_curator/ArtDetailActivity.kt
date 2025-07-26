package com.ai_curator

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ai_curator.data.ArtworkRepository
import com.ai_curator.databinding.ActivityArtDetailBinding
import com.ai_curator.viewmodels.ArtDetailViewModel

class ArtDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArtDetailBinding
    private val viewModel: ArtDetailViewModel by viewModels()
    private lateinit var pagerAdapter: ArtworkAdapter
    private lateinit var data : ArtistProfile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        data = ArtistProfile()

        val artistName = intent.getStringExtra("artistName") ?: run {
            Log.w("ArtDetailActivity", "No artistName received!")
            finish()
            return
        }
        Log.d("IntentResult", "뱓은 artistName: $artistName")

        // viewmodel 통해 데이터 로드
        viewModel.loadArtworksByArtistName(artistName)

        pagerAdapter = ArtworkAdapter(emptyList())
        binding.imagePager.adapter = pagerAdapter

        // observe
        viewModel.artworksByArtist.observe(this) { artworks ->
            pagerAdapter.setArtworks(artworks) // 데이터만 바꿔주기
            if (artworks.isEmpty()) {
                binding.artist.text = "$artistName 작가의 작품이 없습니다"
            } else {
                binding.artist.text = "$artistName 작가의 작품 ${artworks.size}점"

                // 예시 : 첫 작품 화면 렌더링
                val artwork = artworks.first()
                binding.title.text = artwork.title
                binding.artist.text = artwork.artist
                binding.description.text = artwork.desc
                //binding.imagePager.setImageResource(artwork.artImageRes)

//                viewPager = binding.imagePager
//                pagerAdapter = ArtworkAdapter(ArtworkRepository.artworks)
//                viewPager.adapter = pagerAdapter
            }
            Log.d("ArtDetail", "Filtered artworks: ${artworks.size}")
        }

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