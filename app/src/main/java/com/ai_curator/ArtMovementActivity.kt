package com.ai_curator

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.ai_curator.data.ArtistRepository
import com.ai_curator.databinding.ActivityArtMovementBinding
import com.ai_curator.databinding.ItemRecyclerviewBinding
import com.ai_curator.viewmodels.ArtMovementViewModel

class ArtMovementActivity : AppCompatActivity() {
    private lateinit var artistAdapter: ArtistAdapter
    private val binding by lazy { ActivityArtMovementBinding.inflate(layoutInflater) }
    val viewModel: ArtMovementViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // item 초기화
        val artistItemList = ArrayList<ArtistProfile>()
        // 기존 데이터 지우고 새로 추가하고 싶으면
        // artistItemList.clear()
        artistItemList.addAll(ArtistRepository.artists)

        // adapter 초기화
        artistAdapter = ArtistAdapter(artistItemList)

        // 작가 프로필 카드 초기화
        viewModel.loadArtistProfile()
        Log.d("ArtMovementActivity", "onCreate: ${viewModel.loadArtistProfile()}")

        artistAdapter.onArtistProfileSetOnClickListener(object : ArtistProfileSetOnClickListener {
            override fun artistItemClickLister(
                itemData: ArtistProfile,
                binding: ItemRecyclerviewBinding
            ) {
                val intent = Intent(this@ArtMovementActivity, ArtDetailActivity::class.java).apply {
                    putExtra("artistName", itemData.artistName)
                }
                startActivity(intent)
                }
        })

        binding.movementGrid.adapter = artistAdapter
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