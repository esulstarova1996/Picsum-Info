package com.impaladigital.photo_interactors

import com.impaladigital.photo_datasource.network.EndPoints

class GetFilteredImageUrl {

    operator fun invoke(photoId: String, isGrayScale: Boolean, blurStrength: Int): String {

        var modifiedPhotoUrl = EndPoints.PICSUM_PHOTO + "/$photoId/$DEFAULT_WIDTH/$DEFAULT_HEIGHT"

        if (isGrayScale) {
            modifiedPhotoUrl += "?$KEY_GREYSCALE"
        }

        if (blurStrength > 0) {
            modifiedPhotoUrl += if (isGrayScale)
                "&$KEY_BLUR=$blurStrength"
            else
                "?$KEY_BLUR=$blurStrength"
        }

        return modifiedPhotoUrl
    }


    companion object {
        const val KEY_GREYSCALE = "grayscale"
        const val KEY_BLUR = "blur"

        const val DEFAULT_WIDTH = "300"
        const val DEFAULT_HEIGHT = "300"
    }
}