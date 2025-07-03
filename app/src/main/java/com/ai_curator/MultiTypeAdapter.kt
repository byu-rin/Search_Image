package com.ai_curator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.ai_curator.databinding.ActivityArtDetailBinding
import com.ai_curator.databinding.ActivityMainBinding
import com.ai_curator.databinding.ItemImageBinding
import com.ai_curator.databinding.ItemRecyclerviewBinding
import dagger.Binds
import java.util.Collections.list

class MultiTypeAdapter(
    private val itemList: List<ArtWorkItem>,
    private val viewType: ViewType
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
        return when (viewType) {
            ViewType.ART_PROFILE -> ViewType.ART_PROFILE.ordinal
            ViewType.IMAGE_SLIDER -> ViewType.IMAGE_SLIDER.ordinal
        }
    }

    override fun getItemCount() = itemList.size
}