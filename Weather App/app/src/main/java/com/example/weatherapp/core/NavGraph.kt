package com.example.weatherapp.core

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weatherapp.features.favorite.viewmodel.FavoriteViewModel
import com.example.weatherapp.features.favorite.views.FavoriteMapView
import com.example.weatherapp.features.favorite.views.FavoriteView
import com.example.weatherapp.features.favorite.views.SelectedFav
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
    homeViewModel: HomeViewModel,
    settingsViewModel: SettingsViewModel,
    locationViewModel: LocationViewModel,
    notificationViewModel: NotificationViewModel,
    favoriteViewModel: FavoriteViewModel,
    unit: String,
    lang: String
) {
    NavHost(navController, startDestination = NavViewRoute.HOME) {
        composable(NavViewRoute.HOME) { HomeView(homeViewModel) }
        composable(NavViewRoute.SETTINGS) {
            SettingView(
                navController, settingsViewModel, homeViewModel, locationViewModel
            )
        }
        composable(NavViewRoute.MAP) { MapView(navController, homeViewModel, settingsViewModel) }
        composable(NavViewRoute.NOTIFICATION) { NotificationView(notificationViewModel) }
        composable(NavViewRoute.FAVORITE) { FavoriteView(navController, favoriteViewModel, unit, lang) }
        composable(NavViewRoute.FAV_MAP) {
            FavoriteMapView(
                navController,
                homeViewModel,
                favoriteViewModel
            )
        }

        composable(NavViewRoute.SELECTED_FAV) { SelectedFav(favoriteViewModel) }

    }
}