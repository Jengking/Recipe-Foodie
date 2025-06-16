package com.fortitude.recipefoodie.vm

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fortitude.recipefoodie.data.DbRepository
import com.fortitude.recipefoodie.events.AddEvents
import com.fortitude.recipefoodie.models.EntryInputStateModel
import com.fortitude.recipefoodie.models.MealItem
import com.fortitude.recipefoodie.models.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val dbRepository: DbRepository
): ViewModel() {
    private val _nameContent = mutableStateOf(EntryInputStateModel(
        hint = "Recipe Name"
    ))
    val nameContent: State<EntryInputStateModel> = _nameContent

    private val _ingredientsContent = mutableStateOf(EntryInputStateModel(
        hint = "Ingredients & Instructions"
    ))
    val ingredientsContent: State<EntryInputStateModel> = _ingredientsContent

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvents(event: AddEvents) {
        when(event) {
            is AddEvents.OnNameChanged -> {
                _nameContent.value = _nameContent.value.copy(text = event.name, isHintVisible = event.name.isEmpty())
            }
            is AddEvents.OnIngredientsChanged -> {
                _ingredientsContent.value = _ingredientsContent.value.copy(text = event.ingredients, isHintVisible = event.ingredients.isEmpty())
            }
            is AddEvents.OnSave -> {
                saveData()
            }
        }
    }

    private fun saveData() {
        viewModelScope.launch {
            try {
                val item = MealItem(
                    id = 0,
                    strMeal = nameContent.value.text,
                    strMealThumb = "",
                    idMeal = "-1",
                    instructions = ingredientsContent.value.text
                )
                dbRepository.insertOwnMeal(item)
                _eventFlow.emit(UiEvent.NavigateBack)
            } catch (e: Exception) {
                _eventFlow.emit(UiEvent.ShouldShowSnackbar("Something went wrong"))
            }
        }
    }
}
















