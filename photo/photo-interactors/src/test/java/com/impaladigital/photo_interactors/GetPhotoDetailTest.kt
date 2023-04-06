package com.impaladigital.photo_interactors

import com.impaladigital.photo_datasource_test.PhotoServiceFake
import com.impaladigital.photo_datasource_test.PhotoServiceResponseType
import com.impaladigital.photo_datasource_test.data.ValidPhotoData
import com.impaladigital.photo_datasource_test.serializePhotoData
import com.impaladigital.photo_domain.PhotoData
import com.impaladigital.photo_interactors.domain.DataState
import com.impaladigital.photo_interactors.domain.ProgressBarState
import com.impaladigital.photo_interactors.domain.UiComponent
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetPhotoDetailTest {

    private lateinit var getPhotoDetail: GetPhotoDetail

    private val heroData = serializePhotoData(ValidPhotoData.data)

    @Test
    fun getPhotoDetail_success() = runBlocking {

        val photoService = PhotoServiceFake.build(
            type = PhotoServiceResponseType.ValidPhotoData
        )

        getPhotoDetail = GetPhotoDetail(photoService)

        val result = getPhotoDetail("0").toList()

        assert(result[0] == DataState.Loading<List<PhotoData>>(ProgressBarState.Loading))

        assert(result[1] is DataState.Data)
        assert(((result[1] as DataState.Data).data as PhotoData) == heroData.first {
            it.id == "0"
        })

        assert(result[2] == DataState.Loading<List<PhotoData>>(ProgressBarState.Idle))

    }

    @Test
    fun getPhotos_malformedDataFirstPage() = runBlocking {

        val photoService = PhotoServiceFake.build(
            type = PhotoServiceResponseType.ValidPhotoData
        )
        getPhotoDetail = GetPhotoDetail(photoService)

        val result = getPhotoDetail("-1").toList()

        assert(result[0] == DataState.Loading<PhotoData>(ProgressBarState.Loading))

        assert(result[1] is DataState.Response)
        assert(((result[1] as DataState.Response).uiComponent as UiComponent.Dialog).title == "Photo")
        assert(((result[1] as DataState.Response).uiComponent as UiComponent.Dialog).description.contains("No data found for photo with id:"))

        assert(result[2] == DataState.Loading<PhotoData>(ProgressBarState.Idle))
    }

}