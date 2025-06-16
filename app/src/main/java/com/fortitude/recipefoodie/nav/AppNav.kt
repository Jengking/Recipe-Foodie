package com.fortitude.recipefoodie.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fortitude.recipefoodie.screens.AddScreen
import com.fortitude.recipefoodie.screens.DetailsScreen
import com.fortitude.recipefoodie.screens.HomeScreen

@Composable
fun AppNav() {
    val controller = rememberNavController()
    NavHost(navController = controller, startDestination = Screens.HomeScreen.name) {
        composable(Screens.HomeScreen.name) {
            HomeScreen(controller)
        }
        composable(Screens.DetailsScreen.name + "/{title}/{idMeal}/{dbId}") { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val idMeal = backStackEntry.arguments?.getString("idMeal") ?: "-1"
            val dbId = backStackEntry.arguments?.getString("dbId") ?: "-1"
            DetailsScreen(controller, title, idMeal, dbId)
        }
        composable(Screens.AddScreen.name) {
            AddScreen(controller)
        }
    }
}