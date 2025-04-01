package com.example.weatherapp.features.home.model

data class WeatherParam(
    var lat: Double = 0.0,
    var lon: Double = 0.0,
    var unit: String = "metric",
    var lang: String = "en",
)