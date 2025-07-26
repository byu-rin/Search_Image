package com.ai_curator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ai_curator.databinding.ItemImageBinding
import com.ai_curator.databinding.ItemRecyclerviewBinding

interface ArtistProfileSetOnClickListener {
    fun artistItemClickLister(itemData: ArtistProfile, binding: ItemRecyclerviewBinding)
}

// 클릭 전달만 담당. 실제 동작은 Viewmodel -> Activity
class ArtworkAdapter(
    private var artWorkItemList: List<Artwork>
) : RecyclerView.Adapter<ArtworkAdapter.ArtWorkViewHolder>() {

    inner class ArtWorkViewHolder(
        val binding: ItemImageBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Artwork) {
            binding.imageView.setImageResource(item.artImageRes)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtWorkViewHolder {
        val binding = ItemImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArtWorkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtWorkViewHolder, position: Int) {
        holder.bind(artWorkItemList[position])
    }

    override fun getItemCount() = artWorkItemList.size

    fun setArtworks(newArtworks: List<Artwork>) {
        this.artWorkItemList = newArtworks
        notifyDataSetChanged()
    }
}

class ArtistAdapter(
    private val artistItemList: List<ArtistProfile>,
) : RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>() {

    // interface 객체 생성
    private var onClickListener: ArtistProfileSetOnClickListener? = null

    // activity 에서 호출 시 객체 초기화
    fun onArtistProfileSetOnClickListener(listener: ArtistProfileSetOnClickListener) {
        this.onClickListener = listener
    }

    // viewholder 클래스 정의 - 하나의 item 레이아웃을 담당
    inner class ArtistViewHolder(
        private val binding: ItemRecyclerviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val artistImage = binding.ivImage
        val artistName = binding.tvName

        fun bind(item: ArtistProfile) {
            artistImage.setImageResource(item.artistImageRes)
            artistName.text = item.artistName

            binding.tvName.text = item.artistName

            // 클릭하고자 하는 view의 리스너에 데이터 던달
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onClickListener?.artistItemClickLister(item, binding)
                }
            }
        }
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
        holder.bind(artistItemList[position])
    }

    override fun getItemCount(): Int {
        return artistItemList.size
    }
}

/*
* click listener 를 onBindViewHolder 에서 구현할 경우
* 생성된 view holder 를 데이터 바인딩될때마다 호출된다.
* 또 아이템을 클릭했을 때 발생시킬 이벤트에 필요한 데이터가 viewModel 이나 view 에 있는 경우
* 또는 fragment 간 화면 전환을 위한 navigation 이 필요한 경우 구현이 불가능하다.
* */


// TODO: 작가페이지 -> 작가 클릭하면 디테일로 이동. 필터 버튼 대신 qr 버튼, 중복 작가는 한명만. 검색기능 추가(검색하면 연검뜨게 하고 버튼 클릭하면 해당 카드뷰만 나오게)
// TODO: 디테일페이지 -> 디테일페이지에 읽어주기 기능 추가