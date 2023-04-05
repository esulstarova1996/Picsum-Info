package com.impaladigital.ui_photolist.ui

import com.impaladigital.photo_domain.PhotoData
import com.impaladigital.photo_interactors.domain.ProgressBarState

data class PhotoListState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val photos: List<PhotoData> = listOf(),
    val currentPageNo : Int = 1
)