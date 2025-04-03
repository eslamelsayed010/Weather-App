package com.example.weatherapp.features.favorite.views

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.core.AppColors
import com.example.weatherapp.core.AppConst
import com.example.weatherapp.core.CustomForecastDivider
import com.example.weatherapp.features.favorite.viewmodel.FavoriteViewModel
import com.example.weatherapp.features.home.model.DailyForecast
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun Custom5NextDayFav(fiveDayForecast: List<DailyForecast>, viewModel: FavoriteViewModel) {
    val context = LocalContext.current
    CustomForecastDivider(context.getString(R.string.Next_five_Days))
    for (element in fiveDayForecast) {
        Custom5NextDayItem(element, viewModel)
        Spacer(Modifier.height(20.dp))
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun Custom5NextDayItem(dailyForecast: DailyForecast, viewModel: FavoriteViewModel) {
    val tempUnit: String = when (viewModel.unit) {
        "metric" -> stringResource(R.string.C_UNIT)
        "standard" -> stringResource(R.string.K_UNIT)
        "imperial" -> stringResource(R.string.F_UNIT)
        else -> AppConst.TEMP_DEGREE
    }

    val context = LocalContext.current
    Box(
        modifier = Modifier
            .shadow(10.dp, RoundedCornerShape(15.dp))
            .border(2.dp, Color.Transparent, RoundedCornerShape(15.dp))
            .background(Color.Transparent.copy(alpha = 0.3f), RoundedCornerShape(15.dp))
            .fillMaxWidth()
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
                    color = Color.White
                )
                GlideImage(
                    imageModel = { AppConst.IMAGE_URL + dailyForecast.weatherIcon + AppConst.IMAGE_EXE },
                    modifier = Modifier
                        .height(100.dp)
                        .width(150.dp)
                )
            }
            Column {
                Row {
                    Row {
                        Text(
                            context.getString(R.string.H) + " " +
                                    String.format("%.1f", dailyForecast.maxTemperature),
                            modifier = Modifier.alignBy { _ -> 0 },
                            fontSize = 20.sp,
                            color = Color.LightGray
                        )
                        Text(
                            tempUnit,
                            color = Color.LightGray,
                            fontSize = 15.sp,
                            modifier = Modifier.alignBy { _ -> 0 }
                        )
                    }
                    Spacer(Modifier.width(5.dp))
                    Row {
                        Text(
                            context.getString(R.string.L) + " " +
                                    String.format("%.1f", dailyForecast.minTemperature),
                            modifier = Modifier.alignBy { _ -> 0 },
                            fontSize = 20.sp,
                            color = Color.LightGray
                        )
                        Text(
                            tempUnit,
                            color = Color.LightGray,
                            fontSize = 15.sp,
                            modifier = Modifier.alignBy { _ -> 0 }
                        )
                    }
                }
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        Text(
                            String.format("%.2f", dailyForecast.avgTemperature),
                            modifier = Modifier.alignBy { _ -> 0 },
                            fontSize = 40.sp,
                            color = Color.White
                        )
                        Text(
                            tempUnit,
                            color = Color.White,
                            fontSize = 30.sp,
                            modifier = Modifier.alignBy { _ -> 0 }
                        )
                    }
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