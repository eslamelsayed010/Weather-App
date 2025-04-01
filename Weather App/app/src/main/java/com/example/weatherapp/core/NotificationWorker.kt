package com.example.weatherapp.core

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.weatherapp.MainActivity

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

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)  // Set the pending intent here
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }


    companion object {
        private const val CHANNEL_ID = "scheduled_notification_channel"
        private const val NOTIFICATION_ID = 1
    }
}
