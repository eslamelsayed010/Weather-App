package com.example.weatherapp.features.notification.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.weatherapp.R
import com.example.weatherapp.core.AppColors

@Composable
fun DayPickerDialog(
    onDismissRequest: () -> Unit,
    onDaySelected: (Int) -> Unit,
    selectedDay: Int
) {
    val days = listOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(Modifier.padding(16.dp)) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.Select_d_day),
                        style = MaterialTheme.typography.headlineSmall,
                        color = AppColors.txtFormField,
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))

                LazyColumn {
                    itemsIndexed(days) { index, day ->
                        val localizedOption = when (day) {
                            "Sunday" -> stringResource(R.string.sunday)
                            "Monday" -> stringResource(R.string.monday)
                            "Tuesday" -> stringResource(R.string.tuesday)
                            "Wednesday" -> stringResource(R.string.wednesday)
                            "Thursday" -> stringResource(R.string.thursday)
                            "Friday" -> stringResource(R.string.friday)
                            "Saturday" -> stringResource(R.string.saturday)
                            else -> day
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onDaySelected(index) }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = index == selectedDay,
                                onClick = { onDaySelected(index) },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = AppColors.txtFormField,
                                )
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = localizedOption,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismissRequest) {
                        Text(stringResource(R.string.Cancel), color = AppColors.txtFormField)
                    }

                    TextButton(
                        colors = ButtonDefaults.buttonColors(AppColors.txtFormField),
                        onClick = { onDaySelected(selectedDay) })
                    {
                        Text(stringResource(R.string.OK))
                    }
                }
            }
        }
    }
}