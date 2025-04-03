package com.example.weatherapp.features.notification.model

import com.example.weatherapp.data.local.LocalDataSource
import kotlinx.coroutines.flow.Flow

class NotificationRepo private constructor(
    private val localDataSource: LocalDataSource
) {
    fun getNotifications(): Flow<List<NotificationModel>> {
        return localDataSource.getNotifications()
    }


    suspend fun addNotification(notificationModel: NotificationModel): Long {
        return localDataSource.insertNotification(notificationModel)
    }

    suspend fun deleteNotification(notificationModel: NotificationModel): Int {
        return localDataSource.deleteNotification(notificationModel)
    }

    companion object {
        private var INSTANCE: NotificationRepo? = null

        fun getInstance(
            localDataSource: LocalDataSource
        ): NotificationRepo {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: NotificationRepo(localDataSource).also {
                    INSTANCE = it
                }
            }
        }
    }
}
