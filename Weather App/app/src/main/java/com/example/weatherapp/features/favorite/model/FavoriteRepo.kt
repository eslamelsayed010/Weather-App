package com.example.weatherapp.features.favorite.model

import com.example.weatherapp.features.home.model.WeatherModel
import com.example.weatherapp.local.LocalDataSource
import com.example.weatherapp.network.RemoteDataSource
import kotlinx.coroutines.flow.Flow

class FavoriteRepo private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {
    fun getCurrentWeather(
        lat: Double,
        lon: Double,
        unit: String,
        lang: String
    ): Flow<WeatherModel> {
        return remoteDataSource.getCurrentWeather(lat, lon, unit, lang)
    }

    fun getFavorites(): Flow<List<FavoriteModel>> {
        return localDataSource.getFavorites()
    }


    suspend fun insertToFavorite(favoriteModel: FavoriteModel): Long {
        return localDataSource.insertToFavorite(favoriteModel)
    }

    suspend fun deleteFromFavorite(favoriteModel: FavoriteModel): Int {
        return localDataSource.deleteFromFavorite(favoriteModel)
    }

    companion object {
        private var INSTANCE: FavoriteRepo? = null

        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localDataSource: LocalDataSource
        ): FavoriteRepo {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: FavoriteRepo(remoteDataSource, localDataSource).also {
                    INSTANCE = it
                }
            }
        }
    }
}
