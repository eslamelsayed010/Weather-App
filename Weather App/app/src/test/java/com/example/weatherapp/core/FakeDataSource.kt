package com.example.weatherapp.core

import com.example.weatherapp.features.favorite.model.FavoriteModel
import com.example.weatherapp.features.home.model.WeatherModel
import com.example.weatherapp.features.notification.model.NotificationModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDataSource(favoriteModel: FavoriteModel, private var weatherModel: WeatherModel) :
    DataSource {

    private val favorites = mutableListOf<FavoriteModel>()

    init {
        favorites.add(favoriteModel)
    }

    override fun getCurrentWeather(
        lat: Double,
        lon: Double,
        unit: String,
        lang: String
    ): Flow<WeatherModel> {
        return flow { emit(weatherModel) }
    }

    override fun getNotifications(): Flow<List<NotificationModel>> {
        TODO()
    }

    override suspend fun insertNotification(notification: NotificationModel): Long {
        TODO()
    }

    override suspend fun deleteNotification(notification: NotificationModel): Int {
        TODO()
    }

    override suspend fun getFavorites(): Flow<List<FavoriteModel>> {
        return flow { emit(favorites.toList()) }
    }

    override suspend fun insertToFavorite(favoriteModel: FavoriteModel): Long {
        favorites.add(favoriteModel)
        return favorites.size.toLong()
    }

    override suspend fun deleteFromFavorite(favoriteModel: FavoriteModel): Int {
        return if (favorites.remove(favoriteModel)) 1 else 0
    }
}