package com.example.weatherapp.features.home.views

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.core.AppConst
import com.example.weatherapp.core.CustomForecastDivider
import com.example.weatherapp.core.models.ThreeHourForecast
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun CustomForecast3Hourly(current3HourForecast: List<ThreeHourForecast>) {
    Column(
        Modifier.fillMaxWidth()
    ) {
        val context = LocalContext.current
        CustomForecastDivider(context.getString(R.string.Hourly_forecast))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(current3HourForecast.size) { index ->
                CustomHourItem(current3HourForecast[index])
            }
        }

    }
}

@SuppressLint("DefaultLocale")
@Composable
fun CustomHourItem(threeHourForecast: ThreeHourForecast) {
    Box(
        modifier = Modifier
            .shadow(10.dp, RoundedCornerShape(50.dp))
            .border(2.dp, Color.Transparent, RoundedCornerShape(50.dp))
            .background(Color.Transparent.copy(alpha = 0.3f), RoundedCornerShape(50.dp))
            .height(200.dp)
            .padding(vertical = 15.dp)
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                threeHourForecast.time,
                color = Color.White,
                fontSize = 20.sp
            )
            GlideImage(
                imageModel = { AppConst.IMAGE_URL + threeHourForecast.weatherIcon + AppConst.IMAGE_EXE },
                modifier = Modifier.size(80.dp)
            )
            Text(
                String.format("%.2f%s", threeHourForecast.temperature, AppConst.TEMP_DEGREE),
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
}