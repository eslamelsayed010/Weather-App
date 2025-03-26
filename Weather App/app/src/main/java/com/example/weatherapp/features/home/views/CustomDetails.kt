package com.example.weatherapp.features.home.views

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.core.models.WeatherModel

@Composable
fun CustomMoreDevitalises(weatherModel: WeatherModel) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp + 100.dp
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .shadow(10.dp, RoundedCornerShape(15.dp))
            .border(2.dp, Color.Transparent, RoundedCornerShape(15.dp))
            .background(Color.Transparent.copy(alpha = 0.3f), RoundedCornerShape(15.dp))
            .width(screenWidth / 2)
            .height(130.dp)
            .padding(8.dp)
    ) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        )
        {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomDetailItem(
                    type = context.getString(R.string.Wind_Speed),
                    unit = context.getString(R.string.M_S),
                    measure = weatherModel.wind.speed
                )
                CustomDetailItem(
                    type = context.getString(R.string.Clouds),
                    unit = "%",
                    measure = weatherModel.clouds.all.toDouble()
                )
            }

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomDetailItem(
                    type = context.getString(R.string.Pressure),
                    unit = context.getString(R.string.hPa),
                    measure = weatherModel.main.pressure.toDouble()
                )
                CustomDetailItem(
                    type = context.getString(R.string.Humidity),
                    unit = "%",
                    measure = weatherModel.main.humidity.toDouble()
                )
            }
        }
    }

}

@SuppressLint("DefaultLocale")
@Composable
fun CustomDetailItem(
    type: String,
    unit: String,
    measure: Double
) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            type,
            color = Color.LightGray,
            fontSize = 15.sp
        )
        Spacer(Modifier.height(3.dp))
        Row {
            Text(
                String.format("%.1f", measure),
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.alignByBaseline()
            )
            Spacer(Modifier.width(5.dp))
            Text(
                unit,
                color = Color.White,
                fontSize = 13.sp,
                modifier = Modifier.alignByBaseline()
            )
        }
    }
}