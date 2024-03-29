package com.impaladigital.photo_interactors.domain

sealed class UiComponent {

    data class Dialog(
        val title: String,
        val description: String,
    ) : UiComponent()

    data class Toast(
        val message: String,
    ) : UiComponent()

    data class None(
        val message: String,
    ) : UiComponent()
}

