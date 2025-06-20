package com.ai_curator

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.ai_curator.data.ArtProfile
import com.ai_curator.data.CategoryItemAdapter
import com.ai_curator.databinding.FragmentArtSearchBinding

class ArtSearchFragment : Fragment() {

    private var _binding: FragmentArtSearchBinding? = null
    private val binding get() = _binding!!

    private var searchStubInflated = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArtSearchBinding.inflate(inflater, container, false)
        inflateSearchBar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewStub은 한 번만 inflate 가능하므로 방지
        if (!searchStubInflated) {
            try {
                binding.searchStub.inflate()
                searchStubInflated = true
            } catch (e: IllegalStateException) {
                Log.e("ArtSearchFragment", "SearchStub already inflated.")
            }
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val artProfiles = listOf(
            ArtProfile(R.drawable.download_1, "한국화"),
            ArtProfile(R.drawable.download_2, "서양화"),
            ArtProfile(R.drawable.download_3, "서예"),
            ArtProfile(R.drawable.download_4, "시각디자인"),
            ArtProfile(R.drawable.download_1, "산업디자인"),
            ArtProfile(R.drawable.download_2, "금속디자인"),
            ArtProfile(R.drawable.download_3, "입체조형")
        )

        binding.movementGrid.apply {
            adapter = CategoryItemAdapter(artProfiles)
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
            addItemDecoration(GridSpacingItemDecoration(2, 16, true))
        }
    }

    private fun inflateSearchBar() {
        val searchStub = binding.searchStub
        searchStub.inflate()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}


/*
문화관
테마 : 핑크퐁 뽀로로 둘리
핑크퐁 : 별, 주름, 하트 -김핑크, 달, 밤, 먼지-박블루, 해, 자외선, 열-이옐로
뽀로로 : 모자, 리본, 멋 -루피, 신발, 장난, 수프-크롱, 팔찌, 수갑, 에어팟-이옐로
둘리 : 포크, 숟갈, 냅킨-김핑크, 조명, 발전기, 감전 -박블루, 뼈, 심장, 쇄골-이옐로

* User interface - login code (organize hash)
아래 내비게이션 왔다갔다 프래그먼트(홈, 검색)
* 홈 - 테마 이미지 위에, 테마 설명, 테마 작품1(이미지만), 테마 작품2(아티클처럼) <테마 설명은 클릭 요소가 없다. 테마 작품1, 2 는 모두 디테일 액티비티로 이동>
* 검색 - 리싸뷰(작가 기준 그리드) 클릭하면 디테일로 이동
* 디테일 -
* */