package com.fortitude.recipefoodie.events

sealed class DetailsEvent {
    data class LoadData(val isOwnRecipe: Boolean = false, val id: String) : DetailsEvent()
}