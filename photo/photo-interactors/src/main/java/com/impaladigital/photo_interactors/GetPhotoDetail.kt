package com.impaladigital.photo_interactors

import com.impaladigital.photo_datasource.network.PhotoService
import com.impaladigital.photo_domain.PhotoData
import com.impaladigital.photo_interactors.domain.DataState
import com.impaladigital.photo_interactors.domain.ProgressBarState
import com.impaladigital.photo_interactors.domain.UiComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPhotoDetail(
    private val photoService: PhotoService,
) {

    operator fun invoke(photoId: String): Flow<DataState<PhotoData>> = flow {

        try {

            emit(DataState.Loading(ProgressBarState.Loading))

            if (photoId != "-1") {

                val photoData: PhotoData? = try {
                    photoService.getPhotoData(photoId)
                } catch (e: Exception) {
                    e.printStackTrace()
                    emit(DataState.Response(
                        uiComponent = UiComponent.Dialog(
                            title = "Network error",
                            description = e.message ?: "Unknown error"
                        )
                    ))

                    null
                }

                emit(DataState.Data(photoData))
            } else {
                emit(DataState.Response(
                    uiComponent = UiComponent.Dialog(
                        title = "Photo",
                        description = "No data found for photo with id: $photoId"
                    )
                ))
            }

        } catch (e: Exception) {
            emit(DataState.Response(
                uiComponent = UiComponent.Dialog(
                    title = "Error",
                    description = e.message ?: "Unknown error"
                )
            ))
        } finally {
            emit(DataState.Loading(ProgressBarState.Idle))
        }
    }
}