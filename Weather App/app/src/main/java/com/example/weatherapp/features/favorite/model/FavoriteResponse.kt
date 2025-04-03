package com.example.weatherapp.features.favorite.model

sealed class FavoriteResponse {
    data object LoadingFavorite : FavoriteResponse()
    data class SuccessFavorite(val data: List<FavoriteModel>): FavoriteResponse()
    data class FailureFavorite(val error: Throwable): FavoriteResponse()
}