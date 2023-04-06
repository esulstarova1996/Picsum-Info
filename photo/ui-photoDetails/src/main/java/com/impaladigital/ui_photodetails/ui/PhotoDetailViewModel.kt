package com.impaladigital.ui_photodetails.ui

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.impaladigital.photo_interactors.GetFilteredImageUrl
import com.impaladigital.photo_interactors.GetPhotoDetail
import com.impaladigital.photo_interactors.domain.DataState
import com.impaladigital.photo_interactors.domain.UiComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    private val getFilteredImageUrl: GetFilteredImageUrl,
    private val getPhotoDetail: GetPhotoDetail,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val photoId: String? = savedStateHandle["photoId"]

    private val _uiState = MutableStateFlow(PhotoDetailState())
    val uiState = _uiState.asStateFlow()

    private val _messageChannel = Channel<UiComponent>()
    val messageChannel = _messageChannel.receiveAsFlow()

    init {
        photoId.takeIf {
            it != null
        }?.run {
            onTriggerEvent(PhotoDetailEvent.GetPhotoDetails(this))
        }
    }

    fun onTriggerEvent(photoDetailEvent: PhotoDetailEvent) {
        when (photoDetailEvent) {
            is PhotoDetailEvent.ApplyFilter -> {
                if (photoId != null && photoId != "-1") {
                    updateImageUrl(photoId,
                        photoDetailEvent.isGrayscale,
                        photoDetailEvent.blurStrength)
                }
            }
            is PhotoDetailEvent.GetPhotoDetails -> {
                getImageDetail(photoDetailEvent.photoId)
            }
        }
    }

    private fun getImageDetail(photoId: String) {
        getPhotoDetail(photoId).onEach { dataState ->

            when (dataState) {
                is DataState.Data -> {
                    if (dataState.data != null) {
                        uiState.value.copy(
                            photoDetail = dataState.data
                        ).also {
                            _uiState.value = it
                        }
                    }
                }
                is DataState.Loading -> {
                    uiState.value.copy(
                        progressBarState = dataState.progressBarState
                    ).also {
                        _uiState.value = it
                    }
                }
                is DataState.Response -> {

                    uiState.value.copy(
                        photoDetail = null
                    ).also {
                        _uiState.value = it
                    }

                    if (dataState.uiComponent is UiComponent.None) {
                        Log.i("getPhotoDetail:",
                            " ${(dataState.uiComponent as UiComponent.None).message}")
                    } else {
                        viewModelScope.launch {
                            _messageChannel.send(dataState.uiComponent)
                        }
                    }
                }
            }

        }.launchIn(viewModelScope)
    }

    private fun updateImageUrl(photoId: String, isGrayScaleApplied: Boolean, blurStrength: Int) {

        val newDetails = uiState.value.photoDetail?.copy(
            download_url = getFilteredImageUrl(photoId,
                isGrayScaleApplied,
                blurStrength,
                uiState.value.photoDetail?.width,
                uiState.value.photoDetail?.height)
        )

        _uiState.value = uiState.value.copy(
            photoDetail = newDetails
        )
    }

}