package com.example.weatherapp.features.main.views

import android.location.Location
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.MainActivity
import com.example.weatherapp.features.home.repo.WeatherRepo
import com.example.weatherapp.features.home.viewmodel.HomeFactory
import com.example.weatherapp.features.home.viewmodel.HomeViewModel
import com.example.weatherapp.features.main.viewmodel.LocationViewModel
import com.example.weatherapp.features.settings.viewmodel.SettingsViewModel
import com.example.weatherapp.network.RetrofitHelper
import com.example.weatherapp.network.WeatherRemoteDataSource

@Composable
fun SetupHomeLocation(
    settingsViewModel: SettingsViewModel,
    location: Location,
    locationViewModel: LocationViewModel,
    requiredActivity: MainActivity,
    unit: String = "metric",
    lang: String = "en"
) {
    val locationPref =
        settingsViewModel.locationPreference.collectAsState(initial = "GPS").value
    val latState = settingsViewModel.mapLat.collectAsState(initial = 0.0).value
    val lonState = settingsViewModel.mapLon.collectAsState(initial = 0.0).value

    val (finalLatitude, finalLongitude) = when {
        locationPref == "Map" && latState != 0.0 && lonState != 0.0 -> {
            Pair(latState, lonState)
        }

        location.latitude != 0.0 && location.longitude != 0.0 -> {
            Pair(location.latitude, location.longitude)
        }

        else -> {
            Pair(0.0, 0.0)
        }
    }
    val homeFactory = HomeFactory(
        WeatherRepo.getInstance(WeatherRemoteDataSource(RetrofitHelper)),
        finalLatitude,
        finalLongitude,
        unit,
        lang
    )
    val weatherViewModel =
        ViewModelProvider(requiredActivity, homeFactory)[HomeViewModel::class.java]
    MainView(
        weatherViewModel,
        settingsViewModel,
        locationViewModel
    )
}