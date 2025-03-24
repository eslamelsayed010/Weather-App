package com.example.weatherapp.features.setting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.features.setting.repo.UserPreferencesRepository
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    // Use the repository's Flow directly
    val langPreference = userPreferencesRepository.languagePreference
    val locationPreference = userPreferencesRepository.locationPreference
    val tempPreference = userPreferencesRepository.temperaturePreference
    val windPreference = userPreferencesRepository.windSpeedPreference

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

class SettingsViewModelFactory(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(userPreferencesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}