package com.ai_curator.data

class ArtWorkRepository {
    fun getArtworks(): List<Artwork> {
        // 테스트용 더미 데이터 반환
        return listOf(
            Artwork(1, "https://...", "작품 설명", "작가 A"),
            Artwork(2, "https://...", "다른 작품", "작가 B")
        )
    }
}