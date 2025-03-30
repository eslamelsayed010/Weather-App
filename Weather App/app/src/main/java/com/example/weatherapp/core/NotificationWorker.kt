package com.example.weatherapp.core

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(
    private val context: Context, workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {

        val title = inputData.getString("NOTIFICATION_TITLE") ?: "Reminder"
        val message = inputData.getString("NOTIFICATION_MESSAGE") ?: "Your scheduled notification"

        showNotification(title, message)

        return Result.success()
    }

    private fun showNotification(title: String, message: String) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID, "Scheduled Notifications", NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Channel for scheduled notifications"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID).setContentTitle(title)
            .setContentText(message).setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT).setAutoCancel(true).build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        private const val CHANNEL_ID = "scheduled_notification_channel"
        private const val NOTIFICATION_ID = 1
    }
}
