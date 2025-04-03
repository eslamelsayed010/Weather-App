package com.example.weatherapp.data.local

import com.example.weatherapp.core.DataSource
import com.example.weatherapp.features.favorite.model.FavoriteModel
import com.example.weatherapp.features.home.model.WeatherModel
import com.example.weatherapp.features.notification.model.NotificationModel
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val dao: Dao) : DataSource {
    override fun getNotifications(): Flow<List<NotificationModel>> {
        return dao.getNotifications()
    }

    override suspend fun insertNotification(notification: NotificationModel): Long {
        return dao.insertNotification(notification)
    }

    override suspend fun deleteNotification(notification: NotificationModel): Int {
        return dao.deleteNotification(notification)
    }

    override suspend fun getFavorites(): Flow<List<FavoriteModel>> {
        return dao.getFavorites()
    }

    override suspend fun insertToFavorite(favoriteModel: FavoriteModel): Long {
        return dao.insertToFavorite(favoriteModel)
    }

    override suspend fun deleteFromFavorite(favoriteModel: FavoriteModel): Int {
        return dao.deleteFromFavorite(favoriteModel)
    }

    override fun getCurrentWeather(
        lat: Double,
        lon: Double,
        unit: String,
        lang: String
    ): Flow<WeatherModel> {
        TODO("Not yet implemented")
    }
}