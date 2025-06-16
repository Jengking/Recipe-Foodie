package com.fortitude.recipefoodie.models

data class EntryInputStateModel(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)