package com.ai_curator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ai_curator.databinding.ItemImageBinding
import com.ai_curator.databinding.ItemRecyclerviewBinding

// 클릭 전달만 담당. 실제 동작은 Viewmodel -> Activity
class ArtworkAdapter(
    private var artWorkItemList: List<Artwork>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

//    fun updateItems(newItems: List<ArtWorkItem>) {
//        itemList = newItems // 외부에서 변경 x
//        notifyDataSetChanged()
//    }

    inner class ArtWorkViewHolder(
        val binding: ItemImageBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Artwork) {
            binding.imageView.setImageResource(item.artImageRes)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ArtWorkViewHolder(
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ArtWorkViewHolder).bind(artWorkItemList[position])
    }

    override fun getItemCount() = artWorkItemList.size
}

class ArtistAdapter(
    private var artistItemList: List<ArtistProfile>,
    // private val onItemClick: (ArtistProfile) -> Unit // 클릭 시 artistId 전달
) : RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>() {

    inner class ArtistViewHolder(
        binding: ItemRecyclerviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val artistImage = binding.ivImage
        val artistName = binding.tvName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val binding = ItemRecyclerviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArtistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.artistImage.setImageResource(artistItemList[position].artistImageRes)
        holder.artistName.text = artistItemList[position].artistName
    }

    override fun getItemCount(): Int {
        return artistItemList.size
    }
}


// TODO: 작가페이지 -> 작가 클릭하면 디테일로 이동. 필터 버튼 대신 qr 버튼, 중복 작가는 한명만. 검색기능 추가(검색하면 연검뜨게 하고 버튼 클릭하면 해당 카드뷰만 나오게)
// TODO: 디테일페이지 -> 디테일페이지에 읽어주기 기능 추가