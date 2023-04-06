package com.impaladigital.photo_interactors

import com.impaladigital.photo_datasource.network.EndPoints
import com.impaladigital.photo_interactors.GetFilteredImageUrl.Companion.DEFAULT_HEIGHT
import com.impaladigital.photo_interactors.GetFilteredImageUrl.Companion.DEFAULT_WIDTH
import com.impaladigital.photo_interactors.GetFilteredImageUrl.Companion.KEY_BLUR
import com.impaladigital.photo_interactors.GetFilteredImageUrl.Companion.KEY_GREYSCALE
import org.junit.Test

class GetFilteredImageUrlTest {

    companion object {
        const val TEST_IMAGE_ID = "1"

        const val TEST_WIDTH = 400
        const val TEST_HEIGHT = 500
    }

    private lateinit var getFilteredImageUrl: GetFilteredImageUrl

    @Test
    fun getFilteredImageUrl_withGrayscaleBlur1NullWidthHeight() {

        getFilteredImageUrl = GetFilteredImageUrl()

        val result = getFilteredImageUrl(TEST_IMAGE_ID, true, 1)

        assert(result == EndPoints.PICSUM_PHOTO + "$TEST_IMAGE_ID/$DEFAULT_WIDTH/$DEFAULT_HEIGHT?$KEY_GREYSCALE&$KEY_BLUR=$TEST_IMAGE_ID")
    }

    @Test
    fun getFilteredImageUrl_noGrayscaleBlur0CustomWidthHeight(){
        getFilteredImageUrl = GetFilteredImageUrl()

        val result = getFilteredImageUrl(TEST_IMAGE_ID, false, 0, TEST_WIDTH, TEST_HEIGHT)

        assert(result == EndPoints.PICSUM_PHOTO + "$TEST_IMAGE_ID/$TEST_WIDTH/$TEST_HEIGHT")

    }
}