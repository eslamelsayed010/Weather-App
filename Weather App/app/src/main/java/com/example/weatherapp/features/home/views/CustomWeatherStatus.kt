package com.example.weatherapp.features.home.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.core.AppConst
import com.example.weatherapp.features.home.model.WeatherModel
import com.example.weatherapp.features.home.viewmodel.HomeViewModel

@SuppressLint("DefaultLocale")
@Composable
fun CustomWeatherStatus(weatherModel: WeatherModel, homeViewModel: HomeViewModel) {
    val context = LocalContext.current
    val txtColor = when (weatherModel.weather.firstOrNull()?.description?.lowercase()) {
        "snow", "light snow", "rain and snow", "blizzard", "Mist" -> Color.Black
        "few clouds" -> Color.LightGray
        else -> Color.White
    }

    val tempUnit: String = when (homeViewModel.unit) {
        "metric" -> stringResource(R.string.C_UNIT)
        "standard" -> stringResource(R.string.K_UNIT)
        "imperial" -> stringResource(R.string.F_UNIT)
        else -> AppConst.TEMP_DEGREE
    }

    Text(
        weatherModel.weather[0].description,
        fontSize = 30.sp,
        color = txtColor
    )
    Spacer(Modifier.height(5.dp))
    Row {
        Row {
            Text(
                context.getString(R.string.H) + " " +
                        String.format("%.2f", weatherModel.main.tempMax),
                modifier = Modifier.alignBy { _ -> 0 },
                fontSize = 22.sp,
                color = Color.Black
            )
            Text(
                tempUnit,
                color = Color.Black,
                fontSize = 15.sp,
                modifier = Modifier.alignBy { _ -> 0 }
            )
        }
        Spacer(Modifier.width(5.dp))
        Row {
            Text(
                context.getString(R.string.L) + " " +
                        String.format("%.2f", weatherModel.main.tempMin),
                modifier = Modifier.alignBy { _ -> 0 },
                fontSize = 22.sp,
                color = Color.Black
            )
            Text(
                tempUnit,
                color = Color.Black,
                fontSize = 15.sp,
                modifier = Modifier.alignBy { _ -> 0 }
            )
        }
    }
}