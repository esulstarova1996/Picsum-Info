package com.impaladigital.photo_interactors

import com.impaladigital.photo_datasource_test.PhotoServiceFake
import com.impaladigital.photo_datasource_test.PhotoServiceResponseType
import com.impaladigital.photo_datasource_test.data.ValidPhotoData.NUM_OF_PHOTOS
import com.impaladigital.photo_domain.PhotoData
import com.impaladigital.photo_interactors.domain.DataState
import com.impaladigital.photo_interactors.domain.ProgressBarState
import com.impaladigital.photo_interactors.domain.UiComponent
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetPhotoPageTest {

    private lateinit var getPhotoPage: GetPhotoPage

    @Test
    fun getPhotos_success() = runBlocking {

        val photoService = PhotoServiceFake.build(
            type = PhotoServiceResponseType.GoodData
        )

        getPhotoPage = GetPhotoPage(photoService)

        val result = getPhotoPage(1).toList()

        assert(result[0] == DataState.Loading<List<PhotoData>>(ProgressBarState.Loading))

        assert(result[1] is DataState.Data)
        assert((result[1] as DataState.Data).data?.size ?: 0 == NUM_OF_PHOTOS)

        assert(result[2] == DataState.Loading<List<PhotoData>>(ProgressBarState.Idle))

    }

    @Test
    fun getPhotos_emptyList() = runBlocking {
        val photoService = PhotoServiceFake.build(
            type = PhotoServiceResponseType.EmptyList
        )

        getPhotoPage = GetPhotoPage(photoService)

        val result = getPhotoPage(1).toList()

        assert(result[0] == DataState.Loading<List<PhotoData>>(ProgressBarState.Loading))

        assert(result[1] is DataState.Data)
        assert((result[1] as DataState.Data).data?.size ?: 0 == 0)

        assert(result[2] == DataState.Loading<List<PhotoData>>(ProgressBarState.Idle))
    }

    @Test
    fun getPhotos_malformedDataFirstPage() = runBlocking {

        val photoService = PhotoServiceFake.build(
            type = PhotoServiceResponseType.MalformedData
        )

        getPhotoPage = GetPhotoPage(photoService)

        val result = getPhotoPage(1).toList()

        assert(result[0] == DataState.Loading<List<PhotoData>>(ProgressBarState.Loading))

        assert(result[1] is DataState.Response)
        assert(((result[1] as DataState.Response).uiComponent as UiComponent.Dialog).title == "Network error")

        assert(result[2] is DataState.Data)
        assert((result[2] as DataState.Data).data?.size ?: 0 == 0)

        assert(result[3] == DataState.Loading<List<PhotoData>>(ProgressBarState.Idle))
    }

    @Test
    fun getPhotos_malformedDataSecondPage() = runBlocking {

        val photoService = PhotoServiceFake.build(
            type = PhotoServiceResponseType.MalformedData
        )

        getPhotoPage = GetPhotoPage(photoService)

        val result = getPhotoPage(2).toList()

        assert(result[0] == DataState.Loading<List<PhotoData>>(ProgressBarState.Loading))

        assert(result[1] is DataState.Response)
        assert(((result[1] as DataState.Response).uiComponent as UiComponent.Toast).message == "Network error")

        assert(result[2] is DataState.Data)
        assert((result[2] as DataState.Data).data?.size ?: 0 == 0)

        assert(result[3] == DataState.Loading<List<PhotoData>>(ProgressBarState.Idle))
    }

}