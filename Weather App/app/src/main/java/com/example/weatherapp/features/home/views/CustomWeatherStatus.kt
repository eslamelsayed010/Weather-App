package com.example.weatherapp.features.home.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.core.AppConst
import com.example.weatherapp.core.models.WeatherModel

@SuppressLint("DefaultLocale")
@Composable
fun CustomWeatherStatus(weatherModel: WeatherModel) {
    val context = LocalContext.current
    val txtColor = when (weatherModel.weather.firstOrNull()?.description?.lowercase()) {
        "snow", "light snow", "rain and snow", "blizzard", "Mist" -> Color.Black
        "few clouds" -> Color.LightGray
        else -> Color.White
    }

    Text(
        weatherModel.weather[0].description,
        fontSize = 30.sp,
        color = txtColor
    )
    Spacer(Modifier.height(5.dp))
    Text(
        context.getString(R.string.H) +
                String.format(
                    "%.2f%s",
                    weatherModel.main.tempMax,
                    AppConst.TEMP_DEGREE
                ) +
                context.getString(R.string.L) +
                String.format(
                    "%.2f%s",
                    weatherModel.main.tempMin,
                    AppConst.TEMP_DEGREE
                ),
        fontSize = 22.sp,
        color = Color.Black
    )
}