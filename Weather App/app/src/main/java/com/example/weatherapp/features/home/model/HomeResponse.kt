package com.example.weatherapp.features.home.model


sealed class HomeResponse {
    data object LoadingHome : HomeResponse()
    data class SuccessHome(val data: WeatherModel): HomeResponse()
    data class FailureHome(val error: Throwable): HomeResponse()
}