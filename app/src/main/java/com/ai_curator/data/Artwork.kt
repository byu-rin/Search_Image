package com.ai_curator.data

data class Artwork(
    val id: String,
    val title: String,
    val artist: String,
    val size: String,
    val ingredient: String,
    val desc: String,
    val artImage: List<Int>, // drawable resource id
    val artistImage: List<Int> // drawable resource id
)