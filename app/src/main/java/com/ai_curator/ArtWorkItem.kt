package com.ai_curator

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// repository class
// artist image 와 name 만 movement activity recycler 에 뿌리기
// 프로필 클릭 시 작가 아이디와 작품 아이디 대조 후 detailActivity 로 data intent
// detail activity 에서 artimage 는 viewpager 에 노출, 나머지 tv 에 노출

interface Art

// data source class
@Parcelize
data class Artwork(
    val artworkId: String,
    val artist: String,
    val title: String,
    val size: String,
    val ingredient: String,
    val desc: String,
    val artImageRes: Int // drawable resource id
) : Parcelable, Art

@Parcelize
data class ArtistProfile(
    val artistId: String = "",
    val artistImageRes: Int = 0,
    val artistName: String = ""
) : Parcelable, Art

/* if (ArtistProfile.artistName == Artwork.artist) {
callAll.Artwork
*/