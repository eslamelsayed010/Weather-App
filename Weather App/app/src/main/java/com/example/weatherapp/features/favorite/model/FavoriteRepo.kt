package com.example.weatherapp.features.favorite.model

import com.example.weatherapp.core.DataSource
import com.example.weatherapp.features.home.model.WeatherModel
import kotlinx.coroutines.flow.Flow

class FavoriteRepo private constructor(
    private val remoteDataSource: DataSource,
    private val localDataSource: DataSource
) {
    fun getCurrentWeather(
        lat: Double,
        lon: Double,
        unit: String,
        lang: String
    ): Flow<WeatherModel> {
        return remoteDataSource.getCurrentWeather(lat, lon, unit, lang)
    }

    suspend fun getFavorites(): Flow<List<FavoriteModel>> {
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
            remoteDataSource: DataSource,
            localDataSource: DataSource
        ): FavoriteRepo {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: FavoriteRepo(remoteDataSource, localDataSource).also {
                    INSTANCE = it
                }
            }
        }
    }
}
