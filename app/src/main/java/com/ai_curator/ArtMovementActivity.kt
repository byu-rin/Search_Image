package com.ai_curator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.ai_curator.data.ArtistRepository
import com.ai_curator.databinding.ActivityArtMovementBinding
import com.ai_curator.databinding.ItemRecyclerviewBinding
import com.ai_curator.viewmodels.ArtMovementViewModel
import com.ai_curator.viewmodels.MovementPageUiEvent

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
        binding.movementGrid.adapter = artistAdapter
        binding.movementGrid.layoutManager = GridLayoutManager(this, 2)
        binding.movementGrid.addItemDecoration(
            GridSpacingItemDecoration(
                spanCount = 2,
                spacing = 16,
                includeEdge = true
            )
        )

        // 작가 프로필 카드 초기화
        viewModel.loadArtistProfile()
        Log.d("ArtMovementActivity", "onCreate: ${viewModel.loadArtistProfile()}")

        // ArtistAdapter 클릭 시 ViewModel 에 전달
        // 누가 클릭 이벤트를 책임지는가? 의 책임이 Adapter -> ViewModel 로 분리된 구조
        artistAdapter.onArtistProfileSetOnClickListener(object : ArtistProfileSetOnClickListener {
            override fun artistItemClickLister(
                itemData: ArtistProfile,
                binding: ItemRecyclerviewBinding
            ) {
                viewModel.onArtistProfileClicked(itemData)
                lifecycleScope.launchWhenStarted {
                    viewModel.eventFlow.collect { event ->
                        when (event) {
                            is MovementPageUiEvent.NavigateToDetail -> {
                                val intent = Intent(
                                    this@ArtMovementActivity,
                                    ArtDetailActivity::class.java
                                ).apply {
                                    putExtra("artistName", event.artistProfile.artistName)
                                }
                                startActivity(intent)
                            }

                            is MovementPageUiEvent.ExpandText -> {
                            }
                        }
                    }
                }
            }
        })

        // 검색 기능 연결
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onSearchQueryChanged(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.searchEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                (event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)) {

                val query = binding.searchEditText.text.toString()
                viewModel.onSearchQueryChanged(query)

                // 키보드 내리기
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)

                true  // 이벤트 소비
            } else {
                false
            }
        }

        // viewModel data observing
        viewModel.artistProfileUiState.observe(this) { artistList ->
            artistAdapter.submitList(artistList)
            Log.d("ArtMovementActivity", "Filtered artist list size: ${artistList.size}")
        }

        // event flow 처리 (한 번만 collect)
        lifecycleScope.launchWhenStarted {
            viewModel.eventFlow.collect { event ->
                when (event) {
                    is MovementPageUiEvent.NavigateToDetail -> {
                        val intent = Intent(this@ArtMovementActivity, ArtDetailActivity::class.java).apply {
                            putExtra("artistName", event.artistProfile.artistName)
                        }
                        startActivity(intent)
                    }
                    is MovementPageUiEvent.ExpandText -> { /* 생략 */ }
                }
            }
        }

        viewModel.loadArtistProfile()
    }
}