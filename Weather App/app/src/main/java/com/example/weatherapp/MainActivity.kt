@file:Suppress("OVERRIDE_DEPRECATION")

package com.example.weatherapp

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.core.AppColors
import com.example.weatherapp.core.CustomAnmiLoading
import com.example.weatherapp.core.WeatherRepo
import com.example.weatherapp.features.home.viewmodel.HomeFactory
import com.example.weatherapp.features.home.viewmodel.HomeViewModel
import com.example.weatherapp.features.main.viewmodel.LocationViewModel
import com.example.weatherapp.features.main.views.MainView
import com.example.weatherapp.features.settings.repo.UserPreferencesRepository
import com.example.weatherapp.features.settings.viewmodel.SettingsViewModel
import com.example.weatherapp.features.settings.viewmodel.SettingsViewModelFactory
import com.example.weatherapp.network.RetrofitHelper
import com.example.weatherapp.network.WeatherRemoteDataSource

@Suppress("DEPRECATION")
class MainActivity : ComponentActivity() {

    private lateinit var locationViewModel: LocationViewModel
    private var localPermissionGpsCode = 2

    private lateinit var homeFactory: HomeFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = AppColors.SettingNav.toArgb()

        val settingsFactory =
            SettingsViewModelFactory(UserPreferencesRepository(applicationContext))

        locationViewModel = ViewModelProvider(this)[LocationViewModel::class.java]
        val settingsViewModel =
            ViewModelProvider(this, settingsFactory)[SettingsViewModel::class.java]

        setContent {
            val location =
                locationViewModel.locationState.value ?: locationViewModel.getDefaultLocation()

            SetupHomeLocation(settingsViewModel, location)
        }
    }


    @Composable
    private fun SetupHomeLocation(
        settingsViewModel: SettingsViewModel,
        location: Location
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

        if (finalLatitude != 0.0 && finalLongitude != 0.0) {
            homeFactory = HomeFactory(
                WeatherRepo.getInstance(WeatherRemoteDataSource(RetrofitHelper)),
                finalLatitude,
                finalLongitude
            )
            val weatherViewModel = ViewModelProvider(this, homeFactory)[HomeViewModel::class.java]
            MainView(
                weatherViewModel,
                settingsViewModel,
                locationViewModel
            )
        } else {
            CustomAnmiLoading()
        }
    }

    override fun onStart() {
        super.onStart()
        locationViewModel.onStart(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == localPermissionGpsCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationViewModel.getFreshLocation()
            }
        }
    }
}