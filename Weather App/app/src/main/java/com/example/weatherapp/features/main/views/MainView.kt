package com.example.weatherapp.features.main.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.core.NavViewRoute
import com.example.weatherapp.core.NavigationGraph
import com.example.weatherapp.features.home.viewmodel.HomeViewModel
import com.example.weatherapp.features.settings.viewmodel.SettingsViewModel

@Composable
fun MainView(viewModel: HomeViewModel, settingsViewModel: SettingsViewModel) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute != NavViewRoute.MAP

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(navController, viewModel)
            }
        }
    ) { paddingValues ->
        Box(Modifier.padding(paddingValues)) {
            NavigationGraph(navController, viewModel, settingsViewModel)
        }
    }
}