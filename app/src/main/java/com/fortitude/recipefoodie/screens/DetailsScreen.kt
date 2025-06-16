package com.fortitude.recipefoodie.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fortitude.recipefoodie.components.AppBar
import com.fortitude.recipefoodie.events.DetailsEvent
import com.fortitude.recipefoodie.vm.DetailsViewModel

@Composable
fun DetailsScreen(
    controller: NavController,
    title: String = "",
    idMeal: String = "-1",
    dbId: String = "-1",
    viewModel: DetailsViewModel = hiltViewModel()
) {

    val isOwnRecipe = idMeal == "-1"
    val details by viewModel.mealDetails.observeAsState()

    val scrollState = rememberScrollState()

    LaunchedEffect(true) {
        viewModel.onEvent(DetailsEvent.LoadData(isOwnRecipe, if (isOwnRecipe) dbId else idMeal))
    }

    Scaffold(
        topBar = {
            AppBar(title = "Recipe Details", showBackArrow = true) {
                controller.navigateUp()
            }
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
                    .padding(paddingValues)
            ) {
                Card(
                    modifier = Modifier.fillMaxSize()
                        .padding(8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                            .padding(16.dp)
                            .verticalScroll(scrollState),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = details?.name.toString(), color = Color.Black, fontWeight = FontWeight.Medium, fontSize = 20.sp)
                        Text(text = "Ingredients & Instructions:", color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                        Text(text = details?.instructions.toString(), color = Color.Black)
                    }
                }
            }
        }
    }
}






