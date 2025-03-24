package com.example.weatherapp.features.home.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.core.AppColors
import com.example.weatherapp.core.AppConst
import com.example.weatherapp.core.CustomForecastDivider
import com.example.weatherapp.core.models.DailyForecast
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun Custom5NextDay(fiveDayForecast: List<DailyForecast>) {
    CustomForecastDivider("Next 5 Days")

    for (element in fiveDayForecast) {
        Custom5NextDayItem(element)
        Spacer(Modifier.height(20.dp))
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun Custom5NextDayItem(dailyForecast: DailyForecast) {
    Box(
        modifier = Modifier
            .shadow(10.dp, RoundedCornerShape(15.dp))
            .border(2.dp, Color.Transparent, RoundedCornerShape(15.dp))
            .background(Color.Transparent.copy(alpha = 0.3f), RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .height(200.dp)
            .padding(15.dp)
    ) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    dailyForecast.date,
                    fontSize = 30.sp,
                    color = AppColors.Gray300
                )
                GlideImage(
                    imageModel = { AppConst.IMAGE_URL + dailyForecast.weatherIcon + AppConst.IMAGE_EXE },
                    modifier = Modifier
                        .height(100.dp)
                        .width(150.dp)
                )
            }
            Column {
                Text(
                    "H: ${dailyForecast.maxTemperature}${AppConst.TEMP_DEGREE} L: ${dailyForecast.minTemperature}${AppConst.TEMP_DEGREE}",
                    fontSize = 20.sp,
                    color = Color.Gray
                )
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        String.format("%.2f%s", dailyForecast.avgTemperature, AppConst.TEMP_DEGREE),
                        fontSize = 40.sp,
                        color = Color.White
                    )
                    Text(
                        dailyForecast.weatherDescription,
                        fontSize = 22.sp,
                        color = AppColors.Gray300
                    )
                }
            }
        }
    }
}