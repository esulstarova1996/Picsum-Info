package com.impaladigital.photo_interactors

import com.impaladigital.photo_datasource.network.PhotoService

data class PhotoInteractors(
    val getPhotoPage: GetPhotoPage,
    val getFilteredImageUrl: GetFilteredImageUrl,
    val getPhotoDetail: GetPhotoDetail,
) {

    companion object Factory {
        fun build(): PhotoInteractors {
            val service = PhotoService.build()
            return PhotoInteractors(
                getPhotoPage = GetPhotoPage(service),
                getFilteredImageUrl = GetFilteredImageUrl(),
                getPhotoDetail = GetPhotoDetail(service)
            )
        }
    }
}
