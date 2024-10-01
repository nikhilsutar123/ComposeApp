package com.example.composeapp.navigation

sealed class Screen(val route: String) {
    object Home: Screen("home_screen")
    object Details: Screen("detail_screen")
}