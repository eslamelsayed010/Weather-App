package com.example.weatherapp.features.main.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.weatherapp.core.AppColors
import com.example.weatherapp.core.Response
import com.example.weatherapp.core.getNavBK
import com.example.weatherapp.features.home.viewmodel.HomeViewModel
import com.example.weatherapp.features.main.model.BottomNavItem

@Composable
fun BottomNavBar(navController: NavHostController, viewModel: HomeViewModel) {
    val dataState by viewModel.weatherModelResponse.collectAsState()

    // Get current route from navigation controller
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var baseBackgroundColor = Color.Gray
    if (dataState is Response.Success) {
        val weatherModel = (dataState as Response.Success).data
        baseBackgroundColor = getNavBK(weatherModel.weather[0].description)
    }

    val items = listOf(
        BottomNavItem("Home", Icons.Default.Home, "home"),
        BottomNavItem("Profile", Icons.Default.Person, "profile"),
        BottomNavItem("Settings", Icons.Default.Settings, "settings")
    )

    // Determine selected index based on current route
    val selectedItem = items.indexOfFirst { it.route == currentRoute }.takeIf { it >= 0 } ?: 0

    val colors = listOf(
        baseBackgroundColor,
        Color(0xFF4CAF50),
        AppColors.SettingNav
    )

    val backgroundColor = colors[selectedItem]

    NavigationBar(containerColor = backgroundColor) {
        items.forEachIndexed { _, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = Color.White
                    )
                },
                label = { Text(item.label, color = Color.White) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Add navigation options to handle back stack behavior
                        // This prevents building up a large back stack
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination
                        launchSingleTop = true
                        // Restore state when re selecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}