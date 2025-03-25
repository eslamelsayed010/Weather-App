package com.example.weatherapp.features.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.features.settings.repo.UserPreferencesRepository
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val langPreference = userPreferencesRepository.languagePreference
    val locationPreference = userPreferencesRepository.locationPreference
    val tempPreference = userPreferencesRepository.temperaturePreference
    val windPreference = userPreferencesRepository.windSpeedPreference
    val mapLat = userPreferencesRepository.latitude
    val mapLon = userPreferencesRepository.longitude

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
}

@Suppress("UNCHECKED_CAST")
class SettingsViewModelFactory(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SettingsViewModel(userPreferencesRepository) as T
    }
}