package com.example.weatherapp.local

import com.example.weatherapp.features.notification.model.NotificationModel
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val dao: NotificationDao) {
    fun getNotifications(): Flow<List<NotificationModel>> {
        return dao.getNotifications()
    }

    suspend fun insertNotification(notification: NotificationModel): Long {
        return dao.insertNotification(notification)
    }

    suspend fun deleteNotification(notification: NotificationModel): Int {
        return dao.deleteNotification(notification)
    }
}