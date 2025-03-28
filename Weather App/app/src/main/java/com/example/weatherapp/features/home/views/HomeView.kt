package com.example.weatherapp.features.home.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherapp.core.CustomAnmiLoading
import com.example.weatherapp.core.CustomError
import com.example.weatherapp.core.LottieBackgroundBox
import com.example.weatherapp.core.Response
import com.example.weatherapp.core.getAnmiBK
import com.example.weatherapp.features.home.viewmodel.HomeViewModel

@Composable
fun HomeView(viewModel: HomeViewModel) {

    val scrollState = rememberScrollState()

    val current3HourForecast by viewModel.current3HourForecast.observeAsState(emptyList())
    val fiveDayForecast by viewModel.fiveDayForecast.observeAsState(emptyList())
    val dataState by viewModel.weatherModelResponse.collectAsState()

    when {
        dataState is Response.Loading
                && current3HourForecast.isEmpty()
                && fiveDayForecast.isEmpty() -> {
            CustomAnmiLoading()
        }

        dataState is Response.Failure -> {
            CustomError(viewModel)
        }

        dataState is Response.Success -> {
            val weatherModel = (dataState as Response.Success).data
            val anmiBK = getAnmiBK(weatherModel.weather[0].description)
            LottieBackgroundBox(anmiBK) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(15.dp)
                        .verticalScroll(scrollState),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    CustomCountryDataTitle(weatherModel, viewModel)
                    Spacer(Modifier.height(50.dp))
                    CustomCountryIconDegree(weatherModel, viewModel)
                    Spacer(Modifier.height(10.dp))
                    CustomWeatherStatus(weatherModel, viewModel)
                    Spacer(Modifier.height(20.dp))
                    CustomForecast3Hourly(current3HourForecast, viewModel)
                    Spacer(Modifier.height(20.dp))
                    Custom5NextDay(fiveDayForecast, viewModel)
                }
            }
        }
    }
}