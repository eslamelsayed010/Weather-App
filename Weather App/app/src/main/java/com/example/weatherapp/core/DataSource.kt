package com.example.weatherapp.core

import com.example.weatherapp.features.favorite.model.FavoriteModel
import com.example.weatherapp.features.home.model.WeatherModel
import com.example.weatherapp.features.notification.model.NotificationModel
import kotlinx.coroutines.flow.Flow

interface DataSource {
    fun getCurrentWeather(
        lat: Double,
        lon: Double,
        unit: String,
        lang: String
    ): Flow<WeatherModel>

    fun getNotifications(): Flow<List<NotificationModel>>

    suspend fun insertNotification(notification: NotificationModel): Long

    suspend fun deleteNotification(notification: NotificationModel): Int

    suspend fun getFavorites(): Flow<List<FavoriteModel>>

    suspend fun insertToFavorite(favoriteModel: FavoriteModel): Long

    suspend fun deleteFromFavorite(favoriteModel: FavoriteModel): Int
}