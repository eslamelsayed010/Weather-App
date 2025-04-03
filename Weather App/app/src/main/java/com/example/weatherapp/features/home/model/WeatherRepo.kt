package com.example.weatherapp.features.home.model

import com.example.weatherapp.data.network.RemoteDataSource
import kotlinx.coroutines.flow.Flow

class WeatherRepo private constructor(
    private val remoteDataSource: RemoteDataSource
) {
    fun getCurrentWeather(
        lat: Double,
        lon: Double,
        unit: String,
        lang: String
    ): Flow<WeatherModel> {
        return remoteDataSource.getCurrentWeather(lat, lon, unit, lang)
    }

    companion object {
        private var INSTANCE: WeatherRepo? = null

        fun getInstance(
            remoteDataSource: RemoteDataSource
        ): WeatherRepo {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: WeatherRepo(remoteDataSource).also {
                    INSTANCE = it
                }
            }
        }
    }
}