package com.ai_curator.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ai_curator.databinding.ItemRecyclerviewBinding

// Art category profile recycler adapter
class CategoryItemAdapter(
    private val artProfileList: List<ArtProfile>
) : RecyclerView.Adapter<CategoryItemAdapter.PagerViewHolder>() {

    inner class PagerViewHolder(private val binding: ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(artProfile: ArtProfile) {
            binding.ivImage.setImageResource(artProfile.imageResId)
            binding.tvName.text = artProfile.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val binding = ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(artProfileList[position])
    }

    override fun getItemCount(): Int = artProfileList.size
}

data class ArtProfile(
    val imageResId: Int,
    val name: String
)