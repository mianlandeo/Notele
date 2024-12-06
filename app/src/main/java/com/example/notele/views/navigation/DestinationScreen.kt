package com.example.notele.views.navigation

sealed class DestinationScreen(val value: String) {

    data object HomeScreen:DestinationScreen("Home_Screen")
    data object AddScreen:DestinationScreen("Add_Screen")
}