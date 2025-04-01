package com.example.weatherapp.features.notification.views

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.weatherapp.core.AppColors

@SuppressLint("DefaultLocale")
@Composable
fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    onTimeSelected: (Int, Int) -> Unit,
    initialHour: Int,
    initialMinute: Int
) {
    var hour by remember { mutableIntStateOf(initialHour) }
    var minute by remember { mutableIntStateOf(initialMinute) }
    val period = remember { mutableStateOf(if (hour >= 12) "PM" else "AM") }

    val displayHour = remember(hour, period.value) {
        when {
            period.value == "AM" && hour == 0 -> 12
            period.value == "AM" && hour > 12 -> hour - 12
            period.value == "PM" && hour == 0 -> 12
            period.value == "PM" && hour > 12 -> hour - 12
            else -> hour
        }
    }

    val density = LocalDensity.current
    Log.i("TAG", "$density")

    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Select Time",
                    style = MaterialTheme.typography.headlineSmall,
                    color = AppColors.txtFormField,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = String.format("%02d", displayHour),
                        style = MaterialTheme.typography.displayLarge,
                        modifier = Modifier.clickable { })
                    Text(
                        text = ":",
                        style = MaterialTheme.typography.displayLarge,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    Text(text = String.format("%02d", minute),
                        style = MaterialTheme.typography.displayLarge,
                        modifier = Modifier.clickable { })

                    Column(
                        modifier = Modifier.padding(start = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "AM",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = if (period.value == "AM") FontWeight.Bold else FontWeight.Normal,
                            color = if (period.value == "AM") AppColors.txtFormField else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .clickable {
                                    period.value = "AM"
                                    if (hour >= 12) hour -= 12
                                }
                                .padding(vertical = 4.dp, horizontal = 8.dp))

                        Text(text = "PM",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = if (period.value == "PM") FontWeight.Bold else FontWeight.Normal,
                            color = if (period.value == "PM") AppColors.txtFormField else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .clickable {
                                    period.value = "PM"
                                    if (hour < 12) hour += 12
                                }
                                .padding(vertical = 4.dp, horizontal = 8.dp))
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Text(
                        text = "Hour",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4), contentPadding = PaddingValues(4.dp)
                    ) {
                        val hours = if (period.value == "AM") (1..12).toList() else (1..12).toList()
                        items(hours) { h ->
                            val actualHour = when {
                                period.value == "AM" && h == 12 -> 0
                                period.value == "PM" && h == 12 -> 12
                                period.value == "PM" -> h + 12
                                else -> h
                            }

                            val isSelected = hour == actualHour

                            Box(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .size(48.dp)
                                    .background(
                                        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer
                                        else MaterialTheme.colorScheme.surface, shape = CircleShape
                                    )
                                    .clickable { hour = actualHour },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = h.toString(),
                                    color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
                                    else MaterialTheme.colorScheme.onSurface,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                ) {
                    Text(
                        text = "Minute",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Slider(
                        colors = SliderDefaults.colors(
                            activeTrackColor = AppColors.txtFormField,
                            activeTickColor = AppColors.txtFormField,
                        ),
                        value = minute.toFloat(),
                        onValueChange = { minute = it.toInt() },
                        valueRange = 0f..59f,
                        steps = 59,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        listOf(0, 15, 30, 45).forEach { min ->
                            OutlinedButton(
                                onClick = { minute = min },
                                shape = RoundedCornerShape(50),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = if (minute == min) MaterialTheme.colorScheme.primaryContainer
                                    else MaterialTheme.colorScheme.surface
                                )
                            ) {
                                Text(
                                    text = min.toString(),
                                    color = if (minute == min) MaterialTheme.colorScheme.onPrimaryContainer
                                    else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = onDismissRequest, modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Cancel", color = AppColors.txtFormField)
                    }

                    Button(
                        colors = ButtonDefaults.buttonColors(AppColors.txtFormField),
                        onClick = {
                            onTimeSelected(hour, minute)
                        }) {
                        Text("OK")
                    }
                }
            }
        }
    }
}