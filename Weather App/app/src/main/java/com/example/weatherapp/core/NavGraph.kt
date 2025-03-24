package com.example.weatherapp.core

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weatherapp.features.home.views.HomeView
import com.example.weatherapp.features.home.viewmodel.HomeViewModel
import com.example.weatherapp.features.settings.views.MapView
import com.example.weatherapp.features.settings.views.SettingView
import com.example.weatherapp.features.settings.viewmodel.SettingsViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    viewModel: HomeViewModel,
    settingsViewModel: SettingsViewModel
) {
    NavHost(navController, startDestination = "home") {
        composable(NavViewRoute.HOME) { HomeView(viewModel) }
        composable(NavViewRoute.SETTINGS) { SettingView(navController, settingsViewModel) }

        composable(NavViewRoute.MAP) { MapView(navController, viewModel, settingsViewModel) }
    }
}