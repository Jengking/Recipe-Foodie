package com.fortitude.recipefoodie.events

sealed class HomeEvents {
    data class TabSelected(val index: Int) : HomeEvents()
    data object OnLoadData : HomeEvents()
}