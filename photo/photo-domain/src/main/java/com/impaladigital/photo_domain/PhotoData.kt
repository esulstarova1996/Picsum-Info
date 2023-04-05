package com.impaladigital.photo_domain

data class PhotoData(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val download_url: String,
)