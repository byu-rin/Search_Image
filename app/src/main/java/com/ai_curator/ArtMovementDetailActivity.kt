package com.ai_curator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.ai_curator.data.DetailItemAdapter
import com.ai_curator.databinding.ActivityArtMovementDetailBinding

class ArtMovementDetailActivity : AppCompatActivity() {
    private lateinit var viewPager2: ViewPager2
    private lateinit var binding: ActivityArtMovementDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityArtMovementDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewPager2 = binding.imagePager

        val images = listOf(
            R.drawable.download_1,
            R.drawable.download_2,
            R.drawable.download_3,
            R.drawable.download_4,
        )
        val adapter = DetailItemAdapter(images)
        viewPager2.adapter = adapter
    }
}