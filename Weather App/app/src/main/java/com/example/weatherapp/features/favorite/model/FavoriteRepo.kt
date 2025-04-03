package com.example.weatherapp.features.favorite.model

import com.example.weatherapp.local.LocalDataSource
import kotlinx.coroutines.flow.Flow

class FavoriteRepo private constructor(
    private val localDataSource: LocalDataSource
) {
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
            localDataSource: LocalDataSource
        ): FavoriteRepo {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: FavoriteRepo(localDataSource).also {
                    INSTANCE = it
                }
            }
        }
    }
}
