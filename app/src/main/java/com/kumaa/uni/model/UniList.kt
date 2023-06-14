package com.kumaa.uni.model

data class UniList(
    val id: Int,
    val name: String,
    val description: String,
    val location: String,
    val img: Int,
    var isFavorite: Boolean = false,
)
