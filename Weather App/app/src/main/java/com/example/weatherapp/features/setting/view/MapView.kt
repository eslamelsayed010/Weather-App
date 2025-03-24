package com.example.weatherapp.features.setting.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.weatherapp.core.Response
import com.example.weatherapp.features.home.viewmodel.HomeViewModel
import com.example.weatherapp.features.setting.repo.UserPreferencesRepository
import com.example.weatherapp.features.setting.viewmodel.SettingsViewModel
import com.example.weatherapp.features.setting.viewmodel.SettingsViewModelFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@SuppressLint("UnrememberedMutableState")
@Composable
fun MapView(
    navController: NavHostController,
    viewModel: HomeViewModel,
    settingsViewModel: SettingsViewModel = viewModel(
        factory = SettingsViewModelFactory(
            UserPreferencesRepository(LocalContext.current)
        )
    )
) {
    val dataState by viewModel.weatherModelResponse.collectAsState()

    // Default position from weather data
    val selectedPosition = remember {
        mutableStateOf(
            if (dataState is Response.Success) {
                val weatherModel = (dataState as Response.Success).data
                LatLng(weatherModel.coord.lat, weatherModel.coord.lon)
            } else {
                LatLng(0.0, 0.0) // Default position if no weather data
            }
        )
    }

    // Map properties for enabling interactions
    val mapProperties by remember {
        mutableStateOf(
            MapProperties(
                isMyLocationEnabled = true,
                mapType = MapType.NORMAL
            )
        )
    }

    // Map UI settings
    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                zoomControlsEnabled = true,
                myLocationButtonEnabled = true
            )
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = mapProperties,
            uiSettings = mapUiSettings,
            onMapClick = { latLng ->
                // Update the selected position when map is clicked
                selectedPosition.value = latLng
            }
        ) {
            // Add a marker at the selected position
            Marker(
                state = MarkerState(position = selectedPosition.value),
                title = "Selected Location",
                snippet = "Lat: ${selectedPosition.value.latitude}, Lng: ${selectedPosition.value.longitude}"
            )
        }

        // Add a button to save the selected location
        Button(
            onClick = {
                // Save the selected coordinates to your ViewModel
                settingsViewModel.setCustomLocation(
                    selectedPosition.value.latitude,
                    selectedPosition.value.longitude
                )
                Log.i(
                    "MapView",
                    "MapView: ${selectedPosition.value.latitude} ${selectedPosition.value.longitude}"
                )
                // Navigate back to settings
                navController.popBackStack()
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text("Use This Location")
        }

        // Display coordinates
        Text(
            text = "Lat: ${selectedPosition.value.latitude}, Lng: ${selectedPosition.value.longitude}",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .background(Color.White.copy(alpha = 0.7f))
                .padding(8.dp),
            color = Color.Black
        )
    }
}