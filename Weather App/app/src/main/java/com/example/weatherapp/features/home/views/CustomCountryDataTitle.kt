package com.example.weatherapp.features.home.views

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.weatherapp.core.models.WeatherModel
import com.example.weatherapp.features.home.viewmodel.HomeViewModel
import kotlinx.coroutines.delay

@Composable
fun CustomCountryDataTitle(
    weatherModel: WeatherModel?,
    homeViewModel: HomeViewModel
) {
    val cityName = weatherModel?.name?.takeIf { it != "null" } ?: "Unknown Location"
    val countryName = weatherModel?.sys?.country?.takeIf { it != "null" } ?: ""

    val locationText = if (countryName.isNotEmpty()) {
        "$cityName, $countryName"
    } else {
        cityName
    }

    var currentDate by remember { mutableStateOf(homeViewModel.getCurrentFormattedDate()) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            currentDate = homeViewModel.getCurrentFormattedDate()
        }
    }

    val txtColor = when (weatherModel?.weather?.firstOrNull()?.description?.lowercase()) {
        "snow", "light snow", "rain and snow", "Blizzard" -> Color.Black
        "few clouds" -> Color.LightGray
        else -> Color.White
    }

    Text(
        text = locationText,
        fontSize = 25.sp,
        color = txtColor,
        fontWeight = FontWeight.Bold,
    )
    Text(
        text = currentDate,
        fontSize = 18.sp,
        color = txtColor,
    )
}