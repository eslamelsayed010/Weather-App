@file:Suppress("DEPRECATION")

package com.example.weatherapp.features.settings.viewmodel

import android.content.Context
import android.location.Geocoder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.features.settings.repo.UserPreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class SettingsViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    context: Context
) : ViewModel() {

    val langPreference = userPreferencesRepository.languagePreference
    val locationPreference = userPreferencesRepository.locationPreference
    val tempPreference = userPreferencesRepository.temperaturePreference
    val windPreference = userPreferencesRepository.windSpeedPreference
    val mapLat = userPreferencesRepository.latitude
    val mapLon = userPreferencesRepository.longitude
    val langCodePreference = userPreferencesRepository.loadLanguagePreference(context)
    val unitPreference = userPreferencesRepository.loadUnitPreference(context)

    fun setLangCode(context: Context, preference: String) {
        userPreferencesRepository.saveLanguagePreference(context, preference)
    }

    fun setUnit(context: Context, preference: String) {
        userPreferencesRepository.saveUnitPreference(context, preference)
    }

    fun setLangPreference(preference: String) {
        viewModelScope.launch {
            userPreferencesRepository.updateLanguage(preference)
        }
    }

    fun setLocationPreference(preference: String) {
        viewModelScope.launch {
            userPreferencesRepository.updateLocation(preference)
        }
    }

    fun setTempPreference(preference: String) {
        viewModelScope.launch {
            userPreferencesRepository.updateTemperature(preference)
        }
    }

    fun setWindPreference(preference: String) {
        viewModelScope.launch {
            userPreferencesRepository.updateWindSpeed(preference)
        }
    }

    fun setCustomLocation(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            userPreferencesRepository.updateLocation("Map")
            userPreferencesRepository.saveLatitude(latitude)
            userPreferencesRepository.saveLongitude(longitude)
        }
    }

    fun getCityAndCountry(
        context: Context,
        latitude: Double,
        longitude: Double,
        onResult: (String, String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val geocoder = Geocoder(context, Locale.getDefault())
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)

                if (!addresses.isNullOrEmpty()) {
                    val address = addresses[0]
                    val city = address.locality ?: address.subAdminArea ?: "Unknown"
                    val country = address.countryName ?: "Unknown"

                    withContext(Dispatchers.Main) {
                        onResult(city, country)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onResult("Not Found", "Not Found")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onResult("Error", "Error")
                }
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class SettingsViewModelFactory(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SettingsViewModel(userPreferencesRepository, context) as T
    }
}