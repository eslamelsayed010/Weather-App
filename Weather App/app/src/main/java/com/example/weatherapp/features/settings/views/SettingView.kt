package com.example.weatherapp.features.settings.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.weatherapp.R
import com.example.weatherapp.core.AppColors
import com.example.weatherapp.core.AppConst
import com.example.weatherapp.core.CustomSettingsDivider
import com.example.weatherapp.core.LanguageChangeHelper
import com.example.weatherapp.core.NavViewRoute
import com.example.weatherapp.features.home.viewmodel.HomeViewModel
import com.example.weatherapp.features.main.viewmodel.LocationViewModel
import com.example.weatherapp.features.settings.viewmodel.SettingsViewModel

@Composable
fun SettingView(
    navController: NavHostController,
    viewModel: SettingsViewModel,
    homeViewModel: HomeViewModel,
    locationViewModel: LocationViewModel
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val languageChangeHelper by lazy {
        LanguageChangeHelper()
    }

    val locationPreference by viewModel.locationPreference.collectAsState(initial = "GPS")
    val langPreference by viewModel.langPreference.collectAsState(initial = "Arabic")
    val tempPreference by viewModel.tempPreference.collectAsState(initial = "Celsius${AppConst.TEMP_DEGREE}C")
    val windPreference by viewModel.windPreference.collectAsState(initial = "Meter/Sec")

    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("setting.json"))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    val radioOptionsLang = listOf("English", "Arabic", "Default")
    val radioOptionsLocation = listOf("GPS", "Map")
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
            .background(AppColors.BackgroundColor)
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
                CustomSettingsCategory(
                    title = context.getString(R.string.language),
                    icon = R.drawable.lang_ic,
                    radioOptions = radioOptionsLang,
                    selectedOption = langPreference,
                    onOptionSelected = { option ->
                        viewModel.setLangPreference(option)
                        val languageCode = when (option) {
                            "English" -> "en"
                            "Arabic" -> "ar"
                            "Default" -> "Default"
                            else -> "en"
                        }

                        languageChangeHelper.saveLanguagePreference(context, languageCode)
                        languageChangeHelper.changeLanguage(context, languageCode)

                        homeViewModel.refreshWeatherData()
                    }
                )
                CustomSettingsDivider()
                CustomSettingsCategory(
                    title = stringResource(R.string.Location),
                    icon = R.drawable.location_ic,
                    radioOptions = radioOptionsLocation,
                    selectedOption = locationPreference,
                    onOptionSelected = { option ->
                        when (option) {
                            "Map" -> {
                                viewModel.setLocationPreference("Map")
                                navController.navigate(NavViewRoute.MAP)
                            }

                            "GPS" -> {
                                viewModel.setLocationPreference("GPS")
                                val location =
                                    locationViewModel.locationState.value
                                        ?: locationViewModel.getDefaultLocation()
                                homeViewModel.lon = location.longitude
                                homeViewModel.lat = location.latitude
                                homeViewModel.refreshWeatherData()
                            }
                        }
                    }
                )
                CustomSettingsDivider()
                CustomSettingsCategory(
                    title = stringResource(R.string.temp_unit),
                    icon = R.drawable.temp_ic,
                    radioOptions = radioOptionsTemp,
                    selectedOption = tempPreference,
                    onOptionSelected = { option ->
                        viewModel.setTempPreference(option)
                    }
                )
                CustomSettingsDivider()
                CustomSettingsCategory(
                    title = stringResource(R.string.wind_speed_unit),
                    icon = R.drawable.wind_ic,
                    radioOptions = radioOptionsWind,
                    selectedOption = windPreference,
                    onOptionSelected = { option ->
                        viewModel.setWindPreference(option)
                    }
                )
            }
        }

    }
}