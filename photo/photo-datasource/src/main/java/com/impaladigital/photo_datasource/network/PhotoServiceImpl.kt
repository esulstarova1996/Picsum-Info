package com.impaladigital.photo_datasource.network

import com.impaladigital.photo_datasource.network.EndPoints.PICSUM_LIST
import com.impaladigital.photo_datasource.network.EndPoints.PICSUM_PHOTO
import com.impaladigital.photo_datasource.network.model.PhotoDataDto
import com.impaladigital.photo_datasource.network.model.toPhotoData
import com.impaladigital.photo_domain.PhotoData
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class PhotoServiceImpl(private val httpClient: HttpClient) : PhotoService {

    override suspend fun getPhotoList(pageNumber: Int, pageLimit: Int): List<PhotoData> {
        return (httpClient.get {
                    url(PICSUM_LIST)
                    parameter(PARAM_PAGE, pageNumber)
                    parameter(PARAM_LIMIT, pageLimit)
                }.body() as List<PhotoDataDto>
                ).map { it.toPhotoData() }
    }

    override suspend fun getPhotoData(photoId: String): PhotoData {
        return (httpClient.get{
            url("$PICSUM_PHOTO$photoId/info")
        }.body() as PhotoDataDto).toPhotoData()
    }

    companion object{
        const val PARAM_PAGE = "page"
        const val PARAM_LIMIT = "limit"
    }
}