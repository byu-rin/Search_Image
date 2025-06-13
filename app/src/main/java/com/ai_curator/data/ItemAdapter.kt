package com.ai_curator.data

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ai_curator.databinding.ItemImageBinding

class ItemAdapter(
    private val imageList: List<Int>
) : RecyclerView.Adapter<ItemAdapter.PagerViewHolder>() {

    inner class PagerViewHolder(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
        val imageView: ImageView = binding.imageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.imageView.setImageResource(imageList[position])
    }

    override fun getItemCount(): Int = imageList.size
}