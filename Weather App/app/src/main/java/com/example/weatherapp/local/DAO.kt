package com.example.weatherapp.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.features.favorite.model.FavoriteModel
import com.example.weatherapp.features.notification.model.NotificationModel
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Query("SELECT * FROM notification")
    fun getNotifications(): Flow<List<NotificationModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNotification(notification: NotificationModel): Long

    @Delete
    suspend fun deleteNotification(notification: NotificationModel): Int

    @Query("SELECT * FROM favorite")
    fun getFavorites(): Flow<List<FavoriteModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertToFavorite(favorite: FavoriteModel): Long

    @Delete
    suspend fun deleteFromFavorite(favorite: FavoriteModel): Int

}
