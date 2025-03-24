package com.example.weatherapp.features.home.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.weatherapp.core.models.WeatherModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CustomCountryDataTitle(weatherModel: WeatherModel) {
    val dateFormat = SimpleDateFormat("EEEE, d MMMM yyyy | h:mm a", Locale.getDefault())
    val currentDate = dateFormat.format(Date())
    var txtColor = Color.White
    if (weatherModel.weather[0].description == "Snow")
        txtColor = Color.Black
    Text(
        "${weatherModel.name}, ${weatherModel.sys.country}",
        fontSize = 25.sp,
        color = txtColor
    )
    Text(
        currentDate,
        fontSize = 18.sp,
        color = txtColor
    )
}