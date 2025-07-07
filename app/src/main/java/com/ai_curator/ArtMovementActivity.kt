package com.ai_curator

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.ai_curator.databinding.ActivityArtMovementBinding
import com.ai_curator.viewmodels.ArtMovementViewModel

class ArtMovementActivity : AppCompatActivity() {
    private val binding by lazy { ActivityArtMovementBinding.inflate(layoutInflater) }
    val viewModel: ArtMovementViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 작가 프로필 카드 초기화
        viewModel.fetchArtistProfile()

        val adapter = MultiTypeAdapter(
            itemList = emptyList(),
            viewType = ViewType.ART_PROFILE,
            onClick = { item -> viewModel.onArtistProfileClicked(item)}
        )
        binding.movementGrid.adapter = adapter
        binding.movementGrid.layoutManager = GridLayoutManager(this, 2)
        binding.movementGrid.addItemDecoration(
            GridSpacingItemDecoration(
                spanCount = 2,
                spacing = 16,
                includeEdge = true
            )
        )

        // UiState 를 관찰해서 RecyclerView 갱신 필요
        lifecycleScope.launchWhenCreated {
            viewModel.artMovementUiState.collect { state ->
                adapter.updateItems(state.artProfileItems)
            }
        }

        // 프로필 클릭 시 Detail 페이지로 이동
        lifecycleScope.launchWhenCreated {
            viewModel.selectedProfile.collect { profile ->
                profile?.let {
                    // DetailActivity 이동
                    val intent =
                        Intent(this@ArtMovementActivity, ArtDetailActivity::class.java).apply {
                            putExtra("profile", it.imageResId)
                            putExtra("artistName", it.name)
                        }
                    startActivity(intent)
                }
            }
        }
    }
}