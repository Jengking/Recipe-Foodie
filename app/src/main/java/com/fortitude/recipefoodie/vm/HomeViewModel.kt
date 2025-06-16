package com.fortitude.recipefoodie.vm

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fortitude.recipefoodie.core.Constants.BEEF
import com.fortitude.recipefoodie.core.Constants.BREAKFAST
import com.fortitude.recipefoodie.core.Constants.CHICKEN
import com.fortitude.recipefoodie.core.Constants.DESSERT
import com.fortitude.recipefoodie.core.Constants.OWN
import com.fortitude.recipefoodie.core.Constants.PASTA
import com.fortitude.recipefoodie.data.DbRepository
import com.fortitude.recipefoodie.data.MealsApiRepository
import com.fortitude.recipefoodie.events.HomeEvents
import com.fortitude.recipefoodie.models.MealItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MealsApiRepository,
    private val dbRepository: DbRepository
) : ViewModel() {

    val menuList = listOf(BEEF, BREAKFAST, CHICKEN, DESSERT, PASTA, OWN)
    val selectedIndex = mutableIntStateOf(0)

    private val _mealsItems: MutableLiveData<List<MealItem>> = MutableLiveData(emptyList())
    val mealsItems: LiveData<List<MealItem>> = _mealsItems

    init {
        val category = menuList[selectedIndex.intValue]
        loadApiData(category)
    }

    fun onEvent(event: HomeEvents) {
        when(event) {
            is HomeEvents.TabSelected -> {
                selectedIndex.intValue = event.index
                val category = menuList[selectedIndex.intValue]
                if (category != OWN) {
                    //get data from api
                    loadApiData(category)
                } else {
                    //get data from db
                    loadDbData()
                }
            }
            is HomeEvents.OnLoadData -> {
                val category = menuList[selectedIndex.intValue]
                loadApiData(category)
            }
        }
    }

    private fun loadDbData() {
        viewModelScope.launch {
            val items = dbRepository.getOwnMeals()
            if (items.isNullOrEmpty()) {
                _mealsItems.postValue(emptyList())
            } else {
                _mealsItems.postValue(items!!)
            }
        }
    }

    private fun loadApiData(category: String) {
        viewModelScope.launch {
            val response = repository.getMealsByCategoryName(category)
            if (response != null && response.meals.isNotEmpty()) {
                _mealsItems.postValue(response.meals)
            } else {
                _mealsItems.postValue(emptyList())
            }
        }
    }
}