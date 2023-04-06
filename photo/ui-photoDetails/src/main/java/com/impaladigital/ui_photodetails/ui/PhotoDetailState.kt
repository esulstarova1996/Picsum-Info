package com.impaladigital.ui_photodetails.ui

import com.impaladigital.photo_domain.PhotoData
import com.impaladigital.photo_interactors.domain.ProgressBarState

data class PhotoDetailState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val photoDetail: PhotoData? = null,
)
