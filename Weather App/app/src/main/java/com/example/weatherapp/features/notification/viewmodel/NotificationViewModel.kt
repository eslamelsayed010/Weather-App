package com.example.weatherapp.features.notification.viewmodel

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.weatherapp.core.NotificationWorker
import com.example.weatherapp.features.notification.model.NotificationModel
import com.example.weatherapp.features.notification.model.NotificationRepo
import com.example.weatherapp.features.notification.model.NotificationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit

class NotificationViewModel(private val repo: NotificationRepo) : ViewModel() {

    private var mutList =
        MutableStateFlow<NotificationResponse>(NotificationResponse.LoadingNotification)
    val notificationList = mutList.asStateFlow()

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    fun scheduleNotification(
        context: Context,
        day: Int,
        hour: Int,
        minute: Int,
        title: String,
        message: String
    ) {
        val currentTime = LocalDateTime.now()
        val currentDayOfWeek = currentTime.dayOfWeek.value % 7 // Convert to 0-6 format (0 = Sunday)

        val daysToAdd = if (day > currentDayOfWeek) {
            day - currentDayOfWeek
        } else if (day < currentDayOfWeek) {
            7 - (currentDayOfWeek - day)
        } else {
            if (currentTime.toLocalTime().isAfter(LocalTime.of(hour, minute))) {
                7
            } else {
                0
            }
        }

        val scheduledTime = LocalDateTime.of(
            currentTime.toLocalDate().plusDays(daysToAdd.toLong()),
            LocalTime.of(hour, minute)
        )

        val delayInMillis = scheduledTime.atZone(ZoneId.systemDefault()).toInstant()
            .toEpochMilli() - currentTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        val inputData = workDataOf(
            "NOTIFICATION_TITLE" to title, "NOTIFICATION_MESSAGE" to message
        )

        val notificationRequest = OneTimeWorkRequestBuilder<NotificationWorker>().setInitialDelay(
            delayInMillis, TimeUnit.MILLISECONDS
        ).setInputData(inputData).build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "scheduled_notification_${System.currentTimeMillis()}",
            ExistingWorkPolicy.REPLACE,
            notificationRequest
        )
    }

    fun addNotification(notificationModel: NotificationModel) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repo.addNotification(notificationModel)
                }
            } catch (e: Exception) {
                Log.e("TAG", e.toString())
            }
        }
    }

    fun deleteNotification(notificationModel: NotificationModel) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repo.deleteNotification(notificationModel)
                }
            } catch (e: Exception) {
                Log.e("TAG", e.toString())
            }
        }
    }

    fun getLocalData() {
        viewModelScope.launch {
            try {
                val products = repo.getNotifications()
                products
                    .catch { ex ->
                        mutList.value = NotificationResponse.FailureNotification(ex)
                        _toastEvent.emit("Error From DAO: ${ex.message}")

                    }
                    .collect {
                        mutList.value = NotificationResponse.SuccessNotification(it)
                    }
            } catch (e: Exception) {
                Log.e("TAG", e.toString())
                mutList.value = NotificationResponse.FailureNotification(e)
                _toastEvent.emit("Error DAO: ${e.message}")
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class NotificationViewModelFactory(private val repo: NotificationRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotificationViewModel(repo) as T
    }
}
