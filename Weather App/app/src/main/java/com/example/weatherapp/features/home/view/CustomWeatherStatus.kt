package com.example.weatherapp.features.home.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.core.AppConst
import com.example.weatherapp.core.models.WeatherModel

@Composable
fun CustomWeatherStatus(weatherModel: WeatherModel) {
    var txtColor = Color.White
    if (weatherModel.weather[0].description == "Mist")
        txtColor = Color.Black
    Text(
        weatherModel.weather[0].description,
        fontSize = 30.sp,
        color = txtColor
    )
    Spacer(Modifier.height(5.dp))
    Text(
        "H:${weatherModel.main.tempMax}${AppConst.TEMP_DEGREE} L:${weatherModel.main.tempMin}${AppConst.TEMP_DEGREE}",
        fontSize = 22.sp,
        color = Color.Gray
    )
}