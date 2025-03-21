package com.example.weatherapp.core

import com.example.weatherapp.core.models.ForecastModel
import com.example.weatherapp.core.models.WeatherModel
import com.example.weatherapp.network.WeatherRemoteDataSource
import kotlinx.coroutines.flow.Flow

class WeatherRepo private constructor(
    private val remoteDataSource: WeatherRemoteDataSource
) {
    fun getWeatherForecast(lat: Double, lon: Double): Flow<ForecastModel> {
        return remoteDataSource.getWeatherForecast(lat, lon)
    }

    fun getCurrentWeather(lat: Double, lon: Double): Flow<WeatherModel> {
        return remoteDataSource.getCurrentWeather(lat, lon)
    }

    companion object {
        private var INSTANCE: WeatherRepo? = null

        fun getInstance(
            remoteDataSource: WeatherRemoteDataSource
        ): WeatherRepo {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: WeatherRepo(remoteDataSource).also {
                    INSTANCE = it
                }
            }
        }
    }
}