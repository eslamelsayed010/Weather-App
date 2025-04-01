package com.example.weatherapp.features.notification.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notification")
data class NotificationModel(
    @PrimaryKey val date: String,
    val message: String,
    val desc: String
)