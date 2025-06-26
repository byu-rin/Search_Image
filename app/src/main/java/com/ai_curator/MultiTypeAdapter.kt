package com.ai_curator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ai_curator.databinding.ItemImageBinding
import com.ai_curator.databinding.ItemRecyclerviewBinding

class MultiTypeAdapter(
    private val itemList: List<ArtWorkItem>,
    private val viewType: ViewType
) : RecyclerView.Adapter<ArtWorkItemView>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtWorkItemView {
        return when (this.viewType) {
            ViewType.ART_PROFILE -> {
                val binding = ItemRecyclerviewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ArtProfileViewHolder(binding)
            }

            ViewType.IMAGE_SLIDER -> {
                val binding =
                    ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ImageSliderViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ArtWorkItemView, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount() = itemList.size
}