package com.ai_curator

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ai_curator.databinding.ItemImageBinding
import com.ai_curator.databinding.ItemRecyclerviewBinding

// repository class
interface ArtWorkItem

data class ArtistProfileState(
    val isSearched: Boolean = false,
    val artProfileItems: List<ArtProfile> = emptyList()
)

data class ArtProfile(
    val imageResId: Int,
    val name: String?
) : ArtWorkItem

data class ImageItem(val imageResId: Int) : ArtWorkItem

enum class ViewType {
    ART_PROFILE, IMAGE_SLIDER
}

abstract class ArtWorkItemView(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: ArtWorkItem)
}

// Category ArtProfile ViewHolder
class ArtProfileViewHolder(
    private val binding: ItemRecyclerviewBinding
) : ArtWorkItemView(binding.root) {

    override fun bind(item: ArtWorkItem) {
        val profile = item as? ArtProfile ?: return // 안전 캐스팅
        binding.ivImage.setImageResource(profile.imageResId)
        binding.tvName.text = profile.name
    }
}

// Detail ImageSlider ArtImage ViewHolder
class ImageSliderViewHolder(private val binding: ItemImageBinding) : ArtWorkItemView(binding.root) {
    override fun bind(item: ArtWorkItem) {
        val imageItem = item as? ImageItem ?: return // 안전 캐스팅
        binding.imageView.setImageResource(imageItem.imageResId)
    }
}