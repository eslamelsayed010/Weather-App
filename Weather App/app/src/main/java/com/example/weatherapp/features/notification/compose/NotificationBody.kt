package com.example.weatherapp.features.notification.compose

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.weatherapp.R
import com.example.weatherapp.core.NotificationWorker
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit

@SuppressLint("DefaultLocale")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotificationBody(
) {
    val context = LocalContext.current

    var selectedHour by remember { mutableIntStateOf(12) }
    var selectedMinute by remember { mutableIntStateOf(0) }
    var notificationTitle by remember { mutableStateOf("My Notification") }
    var notificationMessage by remember { mutableStateOf("This is a scheduled notification") }
    var showTimePicker by remember { mutableStateOf(false) }
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CustomNotificationTextFromField(
            value = notificationTitle,
            onValueChange = { notificationTitle = it },
            label = "Notification Title"
        )

        CustomNotificationTextFromField(
            value = notificationMessage,
            onValueChange = { notificationMessage = it },
            label = "Notification Message",
            imageVector = Icons.Default.Edit
        )
        Spacer(Modifier.height(30.dp))
        CustomNotificationButton(
            onClick = { showTimePicker = true },
            text = "Select Time: ${String.format("%02d:%02d", selectedHour, selectedMinute)}",
            painter = painterResource(id = R.drawable.schedule)
        )

        if (showTimePicker) {
            TimePickerDialog(
                onDismissRequest = { showTimePicker = false },
                onTimeSelected = { hour, minute ->
                    selectedHour = hour
                    selectedMinute = minute
                    showTimePicker = false
                },
                initialHour = selectedHour,
                initialMinute = selectedMinute
            )
        }

        CustomNotificationButton(
            onClick = {
                scheduleNotification(
                    context, selectedHour, selectedMinute, notificationTitle, notificationMessage
                )
            }, text = "Schedule Notification", painter = painterResource(id = R.drawable.check)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun scheduleNotification(context: Context, hour: Int, minute: Int, title: String, message: String) {
    // Calculate delay until the specified time
    val currentTime = LocalDateTime.now()
    var scheduledTime = LocalDateTime.of(
        currentTime.toLocalDate(), LocalTime.of(hour, minute)
    )

    // If the time is already passed for today, schedule for tomorrow
    if (scheduledTime.isBefore(currentTime)) {
        scheduledTime = scheduledTime.plusDays(1)
    }

    // Calculate the delay in milliseconds
    val delayInMillis = scheduledTime.atZone(ZoneId.systemDefault()).toInstant()
        .toEpochMilli() - currentTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

    // Create input data for the worker
    val inputData = workDataOf(
        "NOTIFICATION_TITLE" to title, "NOTIFICATION_MESSAGE" to message
    )

    // Create a unique work request
    val notificationRequest = OneTimeWorkRequestBuilder<NotificationWorker>().setInitialDelay(
        delayInMillis, TimeUnit.MILLISECONDS
    ).setInputData(inputData).build()

    // Enqueue the work
    WorkManager.getInstance(context).enqueueUniqueWork(
        "scheduled_notification_${System.currentTimeMillis()}",
        ExistingWorkPolicy.REPLACE,
        notificationRequest
    )
}