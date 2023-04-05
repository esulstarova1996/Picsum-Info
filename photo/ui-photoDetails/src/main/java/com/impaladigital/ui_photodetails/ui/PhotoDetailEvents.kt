package com.impaladigital.ui_photodetails.ui

sealed class PhotoDetailEvent {

    data class ApplyFilter(val isGrayscale: Boolean, val blurStrength: Int) : PhotoDetailEvent()

    data class GetPhotoDetails(val photoId: String) : PhotoDetailEvent()

}



