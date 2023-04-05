package com.impaladigital.photo_interactors.domain

sealed class ProgressBarState{

    object Loading: ProgressBarState()

    object Idle: ProgressBarState()
}
