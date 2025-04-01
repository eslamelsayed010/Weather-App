package com.example.weatherapp.features.notification.model

sealed class NotificationResponse {
    data object LoadingNotification : NotificationResponse()
    data class SuccessNotification(val data: List<NotificationModel>): NotificationResponse()
    data class FailureNotification(val error: Throwable): NotificationResponse()
}