package com.example.weatherapp.local

import com.example.weatherapp.features.favorite.model.FavoriteModel
import com.example.weatherapp.features.notification.model.NotificationModel
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val dao: Dao) {
    fun getNotifications(): Flow<List<NotificationModel>> {
        return dao.getNotifications()
    }

    suspend fun insertNotification(notification: NotificationModel): Long {
        return dao.insertNotification(notification)
    }

    suspend fun deleteNotification(notification: NotificationModel): Int {
        return dao.deleteNotification(notification)
    }

    fun getFavorites(): Flow<List<FavoriteModel>> {
        return dao.getFavorites()
    }

    suspend fun insertToFavorite(favoriteModel: FavoriteModel): Long {
        return dao.insertToFavorite(favoriteModel)
    }

    suspend fun deleteFromFavorite(favoriteModel: FavoriteModel): Int {
        return dao.deleteFromFavorite(favoriteModel)
    }
}