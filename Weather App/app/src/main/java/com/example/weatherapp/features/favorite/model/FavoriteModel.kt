package com.example.weatherapp.features.favorite.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteModel(
    @PrimaryKey var city: String,
    var country: String,
    var lat: Double,
    var lon: Double
)