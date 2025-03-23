package com.example.weatherapp.features.setting.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.weatherapp.R
import com.example.weatherapp.core.AppColors
import com.example.weatherapp.core.AppConst
import com.example.weatherapp.core.CustomCatTitle

@Preview(showSystemUi = true, device = Devices.PIXEL_7_PRO)
@Composable
fun SettingView() {
    val scrollState = rememberScrollState()

    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("setting.json"))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    val radioOptionsLang = listOf("English", "Arabic", "Default")
    val radioOptionsLocation = listOf("GPS", "Location")
    val radioOptionsTemp = listOf(
        "Celsius${AppConst.TEMP_DEGREE}C",
        "Kelvin${AppConst.TEMP_DEGREE}K",
        "Fahrenheit${AppConst.TEMP_DEGREE}F"
    )
    val radioOptionsWind = listOf(
        "Meter/Sec",
        "Mile/Hour",
    )

    Column(
        Modifier
            .fillMaxSize()
            .background(AppColors.SettingNav)
            .padding(10.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(200.dp),
            contentScale = ContentScale.FillBounds
        )
        Spacer(Modifier.height(10.dp))
        Box(
            Modifier
                .border(
                    width = 2.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(10.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
            ) {
                CustomCategory("Language", R.drawable.lang_ic, radioOptionsLang)
                CustomDivider()
                CustomCategory("Location", R.drawable.location_ic, radioOptionsLocation)
                CustomDivider()
                CustomCategory("Temp Unit", R.drawable.temp_ic, radioOptionsTemp)
                CustomDivider()
                CustomCategory("Wind Speed Unit", R.drawable.wind_ic, radioOptionsWind)
            }
        }

    }
}

@Composable
private fun CustomCategory(
    title: String,
    icon: Int,
    radioOptions: List<String>
) {
    CustomCatTitle(title, icon)
    CustomRadioButtonGroup(radioOptions)
}

@Composable
private fun CustomDivider() {
    Spacer(Modifier.height(8.dp))
    HorizontalDivider(color = Color.LightGray)
    Spacer(Modifier.height(15.dp))
}

@Composable
fun CustomRadioButtonGroup(radioOptions: List<String>) {

    var selectedOption by remember { mutableStateOf(radioOptions[0]) }

    Row(
        Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        radioOptions.forEach { option ->
            Row(
                modifier = Modifier
                    .selectable(
                        selected = (option == selectedOption),
                        onClick = { selectedOption = option }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (option == selectedOption),
                    onClick = { selectedOption = option },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color(0xFF6200EE),
                        unselectedColor = Color.LightGray,
                        disabledSelectedColor = Color.Gray.copy(alpha = 0.6f),
                        disabledUnselectedColor = Color.Gray.copy(alpha = 0.38f)
                    )
                )
                Text(
                    text = option,
                    color = Color.White,
                )
            }
        }
    }
}
