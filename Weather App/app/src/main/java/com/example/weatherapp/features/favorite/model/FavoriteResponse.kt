package com.example.weatherapp.features.favorite.model

import com.example.weatherapp.features.home.model.WeatherModel

sealed class FavoriteResponse {
    data object LoadingFavorite : FavoriteResponse()
    data class SuccessFavorite(val data: List<FavoriteModel>): FavoriteResponse()
    data class FailureFavorite(val error: Throwable): FavoriteResponse()

    data object LoadingSelectedFavorite : FavoriteResponse()
    data class SuccessSelectedFavorite (val data: WeatherModel): FavoriteResponse()
    data class FailureSelectedFavorite (val error: Throwable): FavoriteResponse()
}