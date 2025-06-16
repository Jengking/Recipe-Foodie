package com.fortitude.recipefoodie.nav

enum class Screens {
    HomeScreen,
    DetailsScreen,
    AddScreen;

    companion object {
        fun fromRoute(route: String?): Screens = when (route?.substringBefore("/")) {
            HomeScreen.name -> HomeScreen
            DetailsScreen.name -> DetailsScreen
            AddScreen.name -> AddScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized.")
        }
    }
}