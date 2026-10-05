package com.secureup.safeclick.ui.navigation

sealed class BottomNavDestinations(val route: String, val title: String) {
    object Analizar : BottomNavDestinations("analizar", "Analizar")
    object Historial : BottomNavDestinations("historial", "Historial")
    object Premium : BottomNavDestinations("premium", "Premium")
}