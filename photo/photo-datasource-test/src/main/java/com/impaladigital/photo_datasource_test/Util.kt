package com.impaladigital.photo_datasource_test

import com.impaladigital.photo_datasource.network.model.PhotoDataDto
import com.impaladigital.photo_datasource.network.model.toPhotoData
import com.impaladigital.photo_domain.PhotoData
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

val json = Json {
    ignoreUnknownKeys = true
}

fun serializePhotoData(jsonData: String): List<PhotoData> {
    val heros: List<PhotoData> =
        json.decodeFromString<List<PhotoDataDto>>(jsonData).map { it.toPhotoData() }
    return heros
}