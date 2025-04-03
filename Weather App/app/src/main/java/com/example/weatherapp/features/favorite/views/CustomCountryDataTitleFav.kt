package com.example.weatherapp.features.favorite.views

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
import com.example.weatherapp.features.favorite.viewmodel.FavoriteViewModel
import com.example.weatherapp.features.home.model.WeatherModel
import kotlinx.coroutines.delay

@Composable
fun CustomCountryDataTitleFav(
    weatherModel: WeatherModel?,
    viewModel: FavoriteViewModel
) {
    val cityName = weatherModel?.name?.takeIf { it != "null" } ?: "Unknown Location"
    val countryName = weatherModel?.sys?.country?.takeIf { it != "null" } ?: ""

    val locationText = if (countryName.isNotEmpty()) {
        "$cityName, $countryName"
    } else {
        cityName
    }

    var currentDate by remember { mutableStateOf(viewModel.getCurrentFormattedDate()) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            currentDate = viewModel.getCurrentFormattedDate()
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