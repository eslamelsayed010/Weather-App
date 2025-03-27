package com.example.weatherapp.network

import com.example.weatherapp.core.models.ForecastModel
import com.example.weatherapp.core.models.WeatherModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WeatherRemoteDataSource(private val service: RetrofitHelper) {
    fun getWeatherForecast(
        lat: Double,
        lon: Double,
        unit: String,
        lang: String
    ): Flow<ForecastModel> = flow {
        emit(
            service.retrofitService.getWeatherForecast(
                lat,
                lon,
                units = unit,
                lang = lang
            )
        )
    }

    fun getCurrentWeather(
        lat: Double,
        lon: Double,
        unit: String,
        lang: String
    ): Flow<WeatherModel> = flow {
        emit(
            service.retrofitService.getCurrentWeather(
                lat,
                lon,
                units = unit,
                lang = lang
            )
        )
    }
}