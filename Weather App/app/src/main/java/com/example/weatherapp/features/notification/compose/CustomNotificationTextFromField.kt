package com.example.weatherapp.features.notification.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.weatherapp.core.AppColors

@Composable
fun CustomNotificationTextFromField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    imageVector: ImageVector = Icons.Default.Notifications
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                label, color = if (value.isEmpty()) Color(0xff7762d8) else AppColors.txtFormField
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = AppColors.txtFormField,
            unfocusedBorderColor = AppColors.txtFormField,
            focusedLabelColor = AppColors.txtFormField,
            unfocusedLabelColor = AppColors.txtFormField,
            cursorColor = AppColors.txtFormField,

            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,

            disabledContainerColor = Color(0xFFF5F5F5),
            focusedTextColor = AppColors.txtFormField,
            unfocusedTextColor = AppColors.txtFormField,
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
        }),
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyLarge,
        leadingIcon = {
            Icon(
                imageVector = imageVector, contentDescription = null, tint = AppColors.txtFormField
            )
        })
}