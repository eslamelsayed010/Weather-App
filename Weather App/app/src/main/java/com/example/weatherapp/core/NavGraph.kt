package com.example.weatherapp.core

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weatherapp.features.home.view.HomeView
import com.example.weatherapp.features.home.viewmodel.HomeViewModel
import com.example.weatherapp.features.setting.view.MapView
import com.example.weatherapp.features.setting.view.SettingView

@Composable
fun NavigationGraph(navController: NavHostController, viewModel: HomeViewModel) {
    NavHost(navController, startDestination = "home") {
        //nav bar
        composable(NavViewRoute.HOME) { HomeView(viewModel) }

        composable(NavViewRoute.SETTINGS) { SettingView(navController) }

        //sitting views
        composable(NavViewRoute.MAP) { MapView(navController, viewModel) }
    }
}