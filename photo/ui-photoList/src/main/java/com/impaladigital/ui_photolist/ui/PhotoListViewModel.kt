package com.impaladigital.ui_photolist.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.impaladigital.photo_domain.PhotoData
import com.impaladigital.photo_interactors.GetPhotoPage
import com.impaladigital.photo_interactors.domain.DataState
import com.impaladigital.photo_interactors.domain.UiComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoListViewModel @Inject constructor(
    private val getPhotoPage: GetPhotoPage,
) : ViewModel() {

    private val _uiState = MutableStateFlow(PhotoListState())
    val uiState = _uiState.asStateFlow()

    private val _messageChannel = Channel<UiComponent>()
    val messageChannel = _messageChannel.receiveAsFlow()

    private var nextPageSyncJob: Job? = null

    init {
        onTriggerEvent(PhotoListEvents.GetPhotoNextPage)
    }

    fun onTriggerEvent(photoListEvents: PhotoListEvents) {
        if (photoListEvents == PhotoListEvents.GetPhotoNextPage) {
            getNextPhotoPage(uiState.value.currentPageNo)
        }
    }

    private fun getNextPhotoPage(pageNumber: Int) {
        if (nextPageSyncJob == null || nextPageSyncJob?.isCompleted == true) {
            nextPageSyncJob?.cancel()

            nextPageSyncJob = getPhotoPage(pageNumber).onEach { dataState ->
                when (dataState) {
                    is DataState.Data -> {
                        uiState.value.copy(
                            photos = uiState.value.photos + (dataState.data as List<PhotoData>),
                            currentPageNo = pageNumber + 1
                        ).also {
                            _uiState.value = it
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
                        if (dataState.uiComponent is UiComponent.None) {
                            Log.i("getPhotoPage:",
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
    }

}