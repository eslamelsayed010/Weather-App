package com.example.weatherapp.features.settings.views

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.weatherapp.R
import com.example.weatherapp.core.CircularAvatar
import com.example.weatherapp.core.Response
import com.example.weatherapp.features.home.viewmodel.HomeViewModel
import com.example.weatherapp.features.settings.viewmodel.SettingsViewModel
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
    settingsViewModel: SettingsViewModel
) {
    val dataState by viewModel.weatherModelResponse.collectAsState()
    var cityName by remember { mutableStateOf("") }
    var countryName by remember { mutableStateOf("") }
    val context = LocalContext.current

    val selectedPosition = remember {
        mutableStateOf(
            if (dataState is Response.Success) {
                val weatherModel = (dataState as Response.Success).data
                LatLng(weatherModel.coord.lat, weatherModel.coord.lon)
            } else
                LatLng(0.0, 0.0)
        )
    }

    val mapProperties by remember {
        mutableStateOf(
            MapProperties(
                isMyLocationEnabled = true,
                mapType = MapType.NORMAL
            )
        )
    }

    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                zoomControlsEnabled = true,
                myLocationButtonEnabled = true
            )
        )
    }

    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = mapProperties,
            uiSettings = mapUiSettings,
            onMapClick = { latLng ->
                selectedPosition.value = latLng
                settingsViewModel.getCityAndCountry(
                    context,
                    selectedPosition.value.latitude,
                    selectedPosition.value.longitude
                ) { city, country ->
                    cityName = city
                    countryName = country
                }
            }
        ) {
            Marker(
                state = MarkerState(position = selectedPosition.value),
                title = "Selected Location",
                snippet = "Lat: ${selectedPosition.value.latitude}, Lng: ${selectedPosition.value.longitude}"
            )
        }
        CustomMapButton(
            viewModel,
            settingsViewModel,
            selectedPosition,
            navController,
            cityName,
            countryName
        )
        Box(
            Modifier
                .padding(15.dp)
                .align(Alignment.TopStart)
                .clickable {
                    navController.popBackStack()
                }
        ) {
            CircularAvatar(R.drawable.arrow_back_ic)
        }
    }
}