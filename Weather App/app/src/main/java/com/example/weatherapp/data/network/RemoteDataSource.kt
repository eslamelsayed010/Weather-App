package com.example.weatherapp.data.network

import com.example.weatherapp.core.DataSource
import com.example.weatherapp.features.favorite.model.FavoriteModel
import com.example.weatherapp.features.home.model.WeatherModel
import com.example.weatherapp.features.notification.model.NotificationModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSource(private val service: RetrofitHelper) : DataSource {
    override fun getCurrentWeather(
        lat: Double,
        lon: Double,
        unit: String,
        lang: String
    ): Flow<WeatherModel> = flow {
        emit(
            RetrofitHelper.retrofitService.getCurrentWeather(
                lat,
                lon,
                units = unit,
                lang = lang
            )
        )
    }

    override fun getNotifications(): Flow<List<NotificationModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertNotification(notification: NotificationModel): Long {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNotification(notification: NotificationModel): Int {
        TODO("Not yet implemented")
    }

    override suspend fun getFavorites(): Flow<List<FavoriteModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertToFavorite(favoriteModel: FavoriteModel): Long {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFromFavorite(favoriteModel: FavoriteModel): Int {
        TODO("Not yet implemented")
    }
}