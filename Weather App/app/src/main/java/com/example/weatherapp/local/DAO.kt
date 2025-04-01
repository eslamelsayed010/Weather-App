package com.example.weatherapp.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.features.notification.model.NotificationModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    @Query("SELECT * FROM notification")
    fun getNotifications(): Flow<List<NotificationModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNotification(notification: NotificationModel): Long

    @Delete
    suspend fun deleteNotification(notification: NotificationModel): Int

}
