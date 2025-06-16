package com.fortitude.recipefoodie.events

sealed class AddEvents {
    data class OnNameChanged(val name: String): AddEvents()
    data class OnIngredientsChanged(val ingredients: String): AddEvents()
    data object OnSave: AddEvents()
}