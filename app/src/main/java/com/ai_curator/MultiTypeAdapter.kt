package com.ai_curator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ai_curator.databinding.ItemImageBinding
import com.ai_curator.databinding.ItemRecyclerviewBinding

class MultiTypeAdapter(
    private val itemList: List<ArtWorkItem>,
//    private val viewType: ViewType
) : RecyclerView.Adapter<ArtWorkItemView>() {

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
    }

    override fun getItemViewType(position: Int): Int {
        return when (itemList[position]) {
            is ArtProfile -> ViewType.ART_PROFILE.ordinal
            is ImageItem -> ViewType.IMAGE_SLIDER.ordinal
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun getItemCount() = itemList.size
}