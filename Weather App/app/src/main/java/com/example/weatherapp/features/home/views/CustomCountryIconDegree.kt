package com.example.weatherapp.features.home.views

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.core.AppConst
import com.example.weatherapp.features.home.model.WeatherModel
import com.example.weatherapp.features.home.viewmodel.HomeViewModel
import com.skydoves.landscapist.glide.GlideImage

@SuppressLint("DefaultLocale")
@Composable
fun CustomCountryIconDegree(weatherModel: WeatherModel, homeViewModel: HomeViewModel) {
    val tempUnit: String = when (homeViewModel.unit) {
        "metric" -> stringResource(R.string.C_UNIT)
        "standard" -> stringResource(R.string.K_UNIT)
        "imperial" -> stringResource(R.string.F_UNIT)
        else -> AppConst.TEMP_DEGREE
    }

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
                Row {
                    Text(
                        String.format("%.2f", weatherModel.main.temp),
                        modifier = Modifier.alignBy { _ -> 0 },
                        fontSize = 60.sp,
                        color = Color.Black
                    )
                    Text(
                        tempUnit,
                        color = Color.Black,
                        fontSize = 35.sp,
                        modifier = Modifier.alignBy { _ -> 0 }
                    )
                }

            }
            GlideImage(
                imageModel = { AppConst.IMAGE_URL + weatherModel.weather[0].icon + AppConst.IMAGE_EXE },
                modifier = Modifier
                    .size(140.dp)
            )

        }
        Spacer(Modifier.height(5.dp))
        CustomMoreDevitalises(weatherModel, homeViewModel)
    }
}