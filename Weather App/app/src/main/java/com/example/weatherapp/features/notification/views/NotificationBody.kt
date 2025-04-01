package com.example.weatherapp.features.notification.views

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
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
import com.example.weatherapp.R
import com.example.weatherapp.features.notification.model.NotificationModel
import com.example.weatherapp.features.notification.viewmodel.NotificationViewModel

@SuppressLint("DefaultLocale")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotificationBody(notificationViewModel: NotificationViewModel) {
    val context = LocalContext.current

    var selectedHour by remember { mutableIntStateOf(12) }
    var selectedMinute by remember { mutableIntStateOf(0) }
    var selectedDay by remember { mutableIntStateOf(0) } // 0 = Sunday
    var notificationTitle by remember { mutableStateOf("My Notification") }
    var notificationMessage by remember { mutableStateOf("This is a scheduled notification") }
    var showTimePicker by remember { mutableStateOf(false) }
    var showDayPicker by remember { mutableStateOf(false) }

    val days = listOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
    val dayText = days[selectedDay]
    val timeText = String.format("%02d:%02d", selectedHour, selectedMinute)
    val dateText = "$dayText, $timeText"

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
            onClick = { showDayPicker = true },
            text = "Select Day: $dayText",
            painter = painterResource(id = R.drawable.day)
        )

        Spacer(Modifier.height(10.dp))

        CustomNotificationButton(
            onClick = { showTimePicker = true },
            text = "Select Time: $timeText",
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

        if (showDayPicker) {
            DayPickerDialog(
                onDismissRequest = { showDayPicker = false },
                onDaySelected = { day ->
                    selectedDay = day
                    showDayPicker = false
                },
                selectedDay = selectedDay
            )
        }

        CustomNotificationButton(
            onClick = {
                notificationViewModel.scheduleNotification(
                    context,
                    selectedDay,
                    selectedHour,
                    selectedMinute,
                    notificationTitle,
                    notificationMessage
                )
                notificationViewModel.addNotification(
                    NotificationModel(
                        date = dateText,
                        message = notificationTitle,
                        desc = notificationMessage
                    )
                )
                Toast.makeText(context, "DONE ✔", Toast.LENGTH_SHORT).show()
            },
            text = "Schedule Notification",
            painter = painterResource(id = R.drawable.check)
        )
    }
}