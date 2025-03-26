package com.example.weatherapp.features.settings.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.core.AppColors

@Composable
fun CustomRadioButtonGroup(
    radioOptions: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Row(
        Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        radioOptions.forEach { option ->
            val localizedOption = when (option) {
                "English" -> stringResource(R.string.language_english)
                "Arabic" -> stringResource(R.string.language_arabic)
                "Default" -> stringResource(R.string.language_default)
                "GPS" -> stringResource(R.string.location_gps)
                "Map" -> stringResource(R.string.location_map)
                "Celsius°C" -> stringResource(R.string.temp_celsius)
                "Kelvin°K" -> stringResource(R.string.temp_kelvin)
                "Fahrenheit°F" -> stringResource(R.string.temp_fahrenheit)
                "Meter/Sec" -> stringResource(R.string.wind_meter_sec)
                "Mile/Hour" -> stringResource(R.string.wind_mile_hour)
                else -> option
            }

            Row(
                modifier = Modifier
                    .selectable(
                        selected = (option == selectedOption),
                        onClick = { onOptionSelected(option) }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (option == selectedOption),
                    onClick = { onOptionSelected(option) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = AppColors.PrimaryColor,
                        unselectedColor = Color.LightGray,
                        disabledSelectedColor = Color.Gray.copy(alpha = 0.6f),
                        disabledUnselectedColor = Color.Gray.copy(alpha = 0.38f)
                    )
                )
                Text(
                    text = localizedOption,
                    color = Color.White,
                )
            }
        }
    }
}