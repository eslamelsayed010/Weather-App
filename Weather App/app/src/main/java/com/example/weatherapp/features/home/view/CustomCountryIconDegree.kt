package com.example.weatherapp.features.home.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.core.AppConst
import com.example.weatherapp.core.models.WeatherModel
import com.skydoves.landscapist.glide.GlideImage

@SuppressLint("DefaultLocale")
@Composable
fun CustomCountryIconDegree(weatherModel: WeatherModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    weatherModel.name,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .align(Alignment.CenterHorizontally),
                    fontSize = 30.sp,
                    color = Color.Black
                )
                Text(
                    String.format("%.2f%s", weatherModel.main.temp, AppConst.TEMP_DEGREE),
                    Modifier.align(Alignment.CenterHorizontally),
                    fontSize = 60.sp,
                    color = Color.Black
                )

            }
            GlideImage(
                imageModel = { AppConst.IMAGE_URL + weatherModel.weather[0].icon + AppConst.IMAGE_EXE },
                modifier = Modifier
                    .size(140.dp)
            )

        }
        Spacer(Modifier.height(5.dp))
        CustomMoreDevitalises(weatherModel)
    }
}