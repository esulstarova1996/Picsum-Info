package com.impaladigital.photo_interactors

import com.impaladigital.photo_datasource.network.PhotoService
import com.impaladigital.photo_domain.PhotoData
import com.impaladigital.photo_interactors.domain.DataState
import com.impaladigital.photo_interactors.domain.ProgressBarState
import com.impaladigital.photo_interactors.domain.UiComponent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class GetPhotoPage(
    private val photoService: PhotoService,
) {

    operator fun invoke(pageNumb: Int): Flow<DataState<List<PhotoData>>> = flow {

        try {
            emit(DataState.Loading(ProgressBarState.Loading))

            delay(1000)

            val photoDataList: List<PhotoData> = try {
                photoService.getPhotoList(pageNumber = pageNumb, pageLimit = DEFAULT_LIMIT)
            } catch (e: Exception) {
                e.printStackTrace()

                if (pageNumb == 1) {
                    emit(DataState.Response(
                        uiComponent = UiComponent.Dialog(
                            title = "Network error",
                            description = e.message ?: "Unknown error"
                        )
                    ))
                } else {
                    emit(DataState.Response(
                        uiComponent = UiComponent.Toast(
                            message = "Network error",
                        )
                    ))
                }

                listOf()
            }

            emit(DataState.Data(photoDataList))

        } catch (e: Exception) {
            if (pageNumb == 1) {
                emit(DataState.Response(
                    uiComponent = UiComponent.Dialog(
                        title = "Error",
                        description = e.message ?: "Unknown error"
                    )
                ))
            } else {
                emit(DataState.Response(
                    uiComponent = UiComponent.Toast(
                        message = "Error getting list",
                    )
                ))
            }
        } finally {
            emit(DataState.Loading(ProgressBarState.Idle))
        }

    }

    companion object {
        private const val DEFAULT_LIMIT = 10
    }

}