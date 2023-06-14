package com.kumaa.uni.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
    object Detail : Screen("home/{uniId}") {
        fun createRoute(uniId: Int) = "home/$uniId"
    }
}