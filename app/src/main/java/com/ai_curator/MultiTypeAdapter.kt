package com.ai_curator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ai_curator.databinding.ItemImageBinding
import com.ai_curator.databinding.ItemRecyclerviewBinding

// 클릭 전달만 담당. 실제 동작은 Viewmodel -> Activity
class MultiTypeAdapter(
    private var itemList: List<ArtWorkItem>,
    private val viewType: ViewType,
    private val onClick: (ArtWorkItem) -> Unit
) : RecyclerView.Adapter<ArtWorkItemView>() {

    fun updateItems(newItems: List<ArtWorkItem>) {
        itemList = newItems // 외부에서 변경 x
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtWorkItemView {
        return when (viewType) {
            ViewType.ART_PROFILE.ordinal -> {
                val binding = ItemRecyclerviewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ArtProfileViewHolder(binding)
            }
            ViewType.IMAGE_SLIDER.ordinal -> {
                val binding =
                    ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ImageSliderViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: ArtWorkItemView, position: Int) {
        holder.bind(itemList[position])
        holder.itemView.setOnClickListener {
            onClick(itemList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (viewType) {
            ViewType.ART_PROFILE -> ViewType.ART_PROFILE.ordinal
            ViewType.IMAGE_SLIDER -> ViewType.IMAGE_SLIDER.ordinal
        }
    }

    override fun getItemCount() = itemList.size
}

// TODO: 작가페이지 -> 작가 클릭하면 디테일로 이동. 필터 버튼 대신 qr 버튼, 중복 작가는 한명만. 검색기능 추가(검색하면 연검뜨게 하고 버튼 클릭하면 해당 카드뷰만 나오게)
// TODO: 디테일페이지 -> 디테일페이지에 읽어주기 기능 추가