package com.example.weatherapp.features.setting.view

import androidx.compose.runtime.Composable
import com.example.weatherapp.core.CustomSettingsTitle

@Composable
fun CustomSettingsCategory(
    title: String,
    icon: Int,
    radioOptions: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    CustomSettingsTitle(title, icon)
    CustomRadioButtonGroup(radioOptions, selectedOption, onOptionSelected)
}