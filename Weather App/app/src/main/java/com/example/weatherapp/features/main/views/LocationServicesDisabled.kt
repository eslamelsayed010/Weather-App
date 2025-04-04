package com.example.weatherapp.features.main.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherapp.core.AppColors

@Composable
fun LocationServicesDisabled(onEnableLocation: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Location services are disabled")
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onEnableLocation,
            colors = ButtonDefaults.buttonColors(
                containerColor = AppColors.BackgroundColor
            )
        ) {
            Text("Enable Location")
        }
    }
}