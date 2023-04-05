package com.impaladigital.photo_datasource.network.model

import com.impaladigital.photo_domain.PhotoData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoDataDto(

    @SerialName("id")
    val id: String,

    @SerialName("author")
    val author: String,

    @SerialName("width")
    val width: Int,

    @SerialName("height")
    val height: Int,


    @SerialName("url")
    val url: String,

    @SerialName("download_url")
    val downloadUrl: String,

    )

fun PhotoDataDto.toPhotoData(): PhotoData {
    return PhotoData(
        id = id,
        author = author,
        width = width,
        height = height,
        download_url = downloadUrl
    )
}

