package com.example.notele.presentation.views.navigation

sealed class DestinationScreen(val value: String) {

    data object ScreenHome: DestinationScreen("Home_Screen")
    data object AddScreen: DestinationScreen("Add_Screen")
}