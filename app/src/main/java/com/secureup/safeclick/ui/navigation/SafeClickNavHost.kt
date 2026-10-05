package com.secureup.safeclick.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.secureup.safeclick.ui.screens.AnalizarScreen
import com.secureup.safeclick.ui.screens.ResultadoScreen
import com.secureup.safeclick.ui.theme.Background
import com.secureup.safeclick.ui.theme.Border
import com.secureup.safeclick.ui.theme.Primary
import com.secureup.safeclick.ui.theme.SurfaceSoft
import com.secureup.safeclick.ui.theme.TextSecondary
import com.secureup.safeclick.ui.viewmodel.AnalisisViewModel

data class NavItem(
    val destination: BottomNavDestinations,
    val icon: ImageVector,
    val label: String
)

@Composable
fun SafeClickNavHost(
    viewModel: AnalisisViewModel = viewModel()
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val navItems = listOf(
        NavItem(BottomNavDestinations.Analizar, Icons.Default.Search, "Analizar"),
        NavItem(BottomNavDestinations.Historial, Icons.Default.History, "Historial"),
        NavItem(BottomNavDestinations.Premium, Icons.Default.Star, "Premium")
    )

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = {
            // Resultado NO debe tener barra inferior
            val currentRoute = currentDestination?.route
            if (currentRoute != "resultado") {
                NavigationBar(
                    containerColor = Background,
                    tonalElevation = 0.dp
                ) {
                    navItems.forEach { item ->
                        val selected = currentDestination?.hierarchy?.any { it.route == item.destination.route } == true
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(item.destination.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Primary,
                                selectedTextColor = Primary,
                                unselectedIconColor = TextSecondary,
                                unselectedTextColor = TextSecondary,
                                indicatorColor = SurfaceSoft
                            )
                        )
                    }
                }
            }
        },
        containerColor = Background
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = BottomNavDestinations.Analizar.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(BottomNavDestinations.Analizar.route) {
                AnalizarScreen(
                    viewModel = viewModel,
                    onAnalizarClick = {
                        navController.navigate("resultado")
                    },
                    onIrAPremium = {
                        navController.navigate(BottomNavDestinations.Premium.route)
                    }
                )
            }
            composable("resultado") {
                ResultadoScreen(
                    resultado = uiState.resultado,
                    url = uiState.urlInput,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
            // Fase 1 solo Analizar y Resultado; Historial/Premium mínimos no requeridos aún
            composable(BottomNavDestinations.Historial.route) {
                Text(
                    text = "Historial (Fase 1)",
                    modifier = Modifier.padding(24.dp),
                    color = TextSecondary
                )
            }
            composable(BottomNavDestinations.Premium.route) {
                Text(
                    text = "Premium (Fase 1)",
                    modifier = Modifier.padding(24.dp),
                    color = TextSecondary
                )
            }
        }
    }
}