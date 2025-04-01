package com.example.weatherapp.core

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weatherapp.features.home.viewmodel.HomeViewModel
import com.example.weatherapp.features.home.views.HomeView
import com.example.weatherapp.features.main.viewmodel.LocationViewModel
import com.example.weatherapp.features.notification.viewmodel.NotificationViewModel
import com.example.weatherapp.features.notification.views.NotificationView
import com.example.weatherapp.features.settings.viewmodel.SettingsViewModel
import com.example.weatherapp.features.settings.views.MapView
import com.example.weatherapp.features.settings.views.SettingView

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph(
    navController: NavHostController,
    viewModel: HomeViewModel,
    settingsViewModel: SettingsViewModel,
    locationViewModel: LocationViewModel,
    notificationViewModel: NotificationViewModel
) {
    NavHost(navController, startDestination = NavViewRoute.HOME) {
        composable(NavViewRoute.HOME) { HomeView(viewModel) }

        composable(NavViewRoute.SETTINGS) {
            SettingView(
                navController, settingsViewModel, viewModel, locationViewModel
            )
        }

        composable(NavViewRoute.MAP) { MapView(navController, viewModel, settingsViewModel) }

        composable(NavViewRoute.NOTIFICATION) { NotificationView(notificationViewModel) }
    }
}