package com.example.weatherapp.network

import com.example.weatherapp.core.AppConst
import com.example.weatherapp.features.home.model.ForecastModel
import com.example.weatherapp.features.home.model.WeatherModel
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherServices {
    @GET("data/2.5/forecast")
    suspend fun getWeatherForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String = AppConst.API_KEY,
        @Query("units") units: String,
        @Query("lang") lang: String
    ): ForecastModel

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String = AppConst.API_KEY,
        @Query("units") units: String,
        @Query("lang") lang: String
    ): WeatherModel
}