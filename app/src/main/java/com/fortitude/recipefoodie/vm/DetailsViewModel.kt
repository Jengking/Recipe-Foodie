package com.fortitude.recipefoodie.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fortitude.recipefoodie.data.DbRepository
import com.fortitude.recipefoodie.data.MealsApiRepository
import com.fortitude.recipefoodie.events.DetailsEvent
import com.fortitude.recipefoodie.models.DetailsDto
import com.fortitude.recipefoodie.models.MealDetailsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: MealsApiRepository,
    private val dbRepository: DbRepository
) : ViewModel() {

    private val _mealDetails: MutableLiveData<DetailsDto> = MutableLiveData(DetailsDto())
    val mealDetails: LiveData<DetailsDto> = _mealDetails

    fun onEvent(event: DetailsEvent) {
        when(event) {
            is DetailsEvent.LoadData -> {
                if (event.isOwnRecipe) {
                    //get data from db
                    loadFromDb(event.id)
                } else {
                    //get data from api
                    loadDataFromApi(event.id)
                }
            }
        }
    }

    private fun loadFromDb(id: String) {
        viewModelScope.launch {
            val item = dbRepository.getOwnMealById(id.toInt())
            if (item != null) {
                val dto = DetailsDto()
                dto.id = item.id.toString()
                dto.name = item.strMeal
                dto.instructions = item.instructions
                _mealDetails.postValue(dto)
            } else {
                _mealDetails.postValue(DetailsDto())
            }
        }
    }

    private fun loadDataFromApi(id: String) {
        viewModelScope.launch {
            val response = repository.getMealsDetailsById(id)
            if (response != null && response.meals.isNotEmpty()) {
                //update ui
                val item = response.meals[0]
                val ingredients = getIngredients(item).trim()
                val instructions = ingredients + "\n\n" + item.strInstructions.trim()
                val dto = DetailsDto()
                dto.id = item.idMeal
                dto.name = item.strMeal
                dto.instructions = instructions
                _mealDetails.postValue(dto)
            } else {
                _mealDetails.postValue(DetailsDto())
            }
        }
    }

    private fun getIngredients(item: MealDetailsItem): String {
        return item.strMeasure1 + " " + item.strIngredient1 + "\n" +
                item.strMeasure2 + " " + item.strIngredient2 + "\n" +
                item.strMeasure3 + " " + item.strIngredient3 + "\n" +
                item.strMeasure4 + " " + item.strIngredient4 + "\n" +
                item.strMeasure5 + " " + item.strIngredient5 + "\n" +
                item.strMeasure6 + " " + item.strIngredient6 + "\n" +
                item.strMeasure7 + " " + item.strIngredient7 + "\n" +
                item.strMeasure8 + " " + item.strIngredient8 + "\n" +
                item.strMeasure9 + " " + item.strIngredient9 + "\n" +
                item.strMeasure10 + " " + item.strIngredient10 + "\n" +
                item.strMeasure11 + " " + item.strIngredient11 + "\n" +
                item.strMeasure12 + " " + item.strIngredient12 + "\n" +
                item.strMeasure13 + " " + item.strIngredient13 + "\n" +
                item.strMeasure14 + " " + item.strIngredient14 + "\n" +
                item.strMeasure15 + " " + item.strIngredient15 + "\n" +
                item.strMeasure16 + " " + item.strIngredient16 + "\n" +
                item.strMeasure17 + " " + item.strIngredient17 + "\n" +
                item.strMeasure18 + " " + item.strIngredient18 + "\n" +
                item.strMeasure19 + " " + item.strIngredient19 + "\n" +
                item.strMeasure20 + " " + item.strIngredient20
    }
}