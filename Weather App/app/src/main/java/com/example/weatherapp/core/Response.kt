package com.example.weatherapp.core

import com.example.weatherapp.core.models.WeatherModel


sealed class Response {
    data object Loading : Response()
    data class Success(val data: WeatherModel): Response()
    data class Failure(val error: Throwable): Response()
}