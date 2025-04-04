package com.example.weatherapp.features.notification.views

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.weatherapp.R
import com.example.weatherapp.core.CustomAnmiLoading
import com.example.weatherapp.core.CustomError
import com.example.weatherapp.features.notification.model.NotificationResponse
import com.example.weatherapp.features.notification.viewmodel.NotificationViewModel

@Composable
fun CustomNotificationItem(notificationViewModel: NotificationViewModel) {
    val context = LocalContext.current

    notificationViewModel.getLocalData()
    val dataState by (notificationViewModel.notificationList).collectAsState()

    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("alert.json"))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    when (dataState) {
        is NotificationResponse.FailureNotification -> {
            CustomError(notificationViewModel.toastEvent)
        }

        is NotificationResponse.LoadingNotification -> {
            CustomAnmiLoading()
        }

        is NotificationResponse.SuccessNotification -> {
            val data = (dataState as NotificationResponse.SuccessNotification).data
            Column {
                for (element in data) {
                    SwipeableNotificationItem(
                        element = element,
                        composition = composition,
                        progress = progress,
                        onDelete = {
                            notificationViewModel.deleteNotification(element)
                            notificationViewModel.cancelScheduledNotification(context, element.date)
                            Toast.makeText(
                                context,
                                context.getString(R.string.Deleted),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            }
        }
    }
}