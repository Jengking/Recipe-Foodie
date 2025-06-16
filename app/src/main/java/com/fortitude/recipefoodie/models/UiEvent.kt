package com.fortitude.recipefoodie.models

sealed class UiEvent {
    data class ShouldShowSnackbar(val message: String): UiEvent()
    data object NavigateBack: UiEvent()
}