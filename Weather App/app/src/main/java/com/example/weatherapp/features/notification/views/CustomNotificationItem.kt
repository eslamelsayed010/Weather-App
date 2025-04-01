package com.example.weatherapp.features.notification.views

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.weatherapp.core.AppColors
import com.example.weatherapp.core.CustomAnmiLoading
import com.example.weatherapp.core.CustomError
import com.example.weatherapp.features.notification.model.NotificationModel
import com.example.weatherapp.features.notification.model.NotificationResponse
import com.example.weatherapp.features.notification.viewmodel.NotificationViewModel
import kotlin.math.roundToInt

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

        NotificationResponse.LoadingNotification -> {
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
                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SwipeableNotificationItem(
    element: NotificationModel,
    composition: LottieComposition?,
    progress: Float,
    onDelete: () -> Unit
) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    val animatedOffsetX by animateFloatAsState(targetValue = offsetX)
    val density = LocalDensity.current
    val deleteThreshold = with(density) { -120.dp.toPx() }

    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
                offsetX = 0f
            },
            title = { Text("Confirm Deletion", fontWeight = FontWeight.Bold) },
            text = { Text("Are you sure you want to delete this notification?") },
            confirmButton = {
                Button(
                    onClick = {
                        onDelete()
                        showDeleteDialog = false
                        offsetX = 0f
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        offsetX = 0f
                    }
                ) {
                    Text("Cancel", color = AppColors.txtFormField)
                }
            }
        )
    }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .border(2.dp, Color.Transparent, RoundedCornerShape(50.dp))
                .fillMaxWidth()
                .height(120.dp)
                .background(
                    Color.Red.copy(alpha = animatedOffsetX / deleteThreshold),
                    RoundedCornerShape(50.dp)
                )
                .padding(end = 16.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.White.copy(alpha = animatedOffsetX / deleteThreshold),
                modifier = Modifier.size(40.dp)
            )
        }

        Box(
            modifier = Modifier
                .offset { IntOffset(animatedOffsetX.roundToInt(), 0) }
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        offsetX = (offsetX + delta).coerceAtMost(0f)
                    },
                    onDragStopped = {
                        if (offsetX < deleteThreshold) {
                            showDeleteDialog = true
                        } else {
                            offsetX = 0f
                        }
                    }
                )
                .background(Color.Transparent)
        ) {
            Column {
                Text(
                    text = element.message,
                    modifier = Modifier.padding(horizontal = 10.dp),
                    color = AppColors.txtFormField,
                    fontSize = 25.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(5.dp))
                Box(
                    modifier = Modifier
                        .shadow(10.dp, RoundedCornerShape(50.dp))
                        .border(2.dp, Color.Transparent, RoundedCornerShape(8.dp))
                        .background(
                            Color.Transparent.copy(alpha = 0.3f),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(
                            PaddingValues(
                                start = 10.dp,
                                top = 16.dp,
                                end = 5.dp,
                                bottom = 16.dp
                            )
                        )
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                element.desc,
                                color = Color.White,
                                fontSize = 20.sp,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.width(250.dp),
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(Modifier.height(5.dp))
                            Text(
                                element.date,
                                color = Color.LightGray,
                                fontSize = 17.sp
                            )
                        }
                        LottieAnimation(
                            composition = composition,
                            progress = { progress },
                            modifier = Modifier.size(60.dp),
                            contentScale = ContentScale.FillBounds
                        )
                    }
                }
                Spacer(Modifier.height(30.dp))
            }
        }
    }
}