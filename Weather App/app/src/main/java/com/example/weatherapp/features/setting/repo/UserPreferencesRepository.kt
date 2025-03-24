package com.example.weatherapp.features.setting.repo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.weatherapp.core.AppConst
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


@Suppress("PrivatePropertyName")
class UserPreferencesRepository(context: Context) {
    // Access the dataStore via the extension property
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val dataStore = context.dataStore

    // Define preference keys
    private object PreferencesKeys {
        val LANGUAGE = stringPreferencesKey("language")
        val LOCATION = stringPreferencesKey("location")
        val TEMPERATURE = stringPreferencesKey("temperature")
        val WIND_SPEED = stringPreferencesKey("wind_speed")
        val LATITUDE = doublePreferencesKey("latitude")
        val LONGITUDE = doublePreferencesKey("longitude")
    }

    // Default values
    private val DEFAULT_LANGUAGE = "Arabic"
    private val DEFAULT_LOCATION = "GPS"
    private val DEFAULT_TEMPERATURE = "Celsius${AppConst.TEMP_DEGREE}C"
    private val DEFAULT_WIND_SPEED = "Meter/Sec"

    // Get preferences as Flows
    val languagePreference: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[PreferencesKeys.LANGUAGE] ?: DEFAULT_LANGUAGE
        }

    val locationPreference: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[PreferencesKeys.LOCATION] ?: DEFAULT_LOCATION
        }

    // Similarly for temperature and wind
    val temperaturePreference: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[PreferencesKeys.TEMPERATURE] ?: DEFAULT_TEMPERATURE
        }

    val windSpeedPreference: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[PreferencesKeys.WIND_SPEED] ?: DEFAULT_WIND_SPEED
        }

    // Functions to update preferences
    suspend fun updateLanguage(language: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.LANGUAGE] = language
        }
    }

    suspend fun updateLocation(location: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.LOCATION] = location
        }
    }

    suspend fun updateTemperature(temperature: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.TEMPERATURE] = temperature
        }
    }

    suspend fun updateWindSpeed(windSpeed: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.WIND_SPEED] = windSpeed
        }
    }

    val latitude: Flow<Double> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.LATITUDE] ?: 0.0
        }

    val longitude: Flow<Double> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.LONGITUDE] ?: 0.0
        }

    suspend fun saveLatitude(lat: Double) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.LATITUDE] = lat
        }
    }

    suspend fun saveLongitude(lng: Double) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.LONGITUDE] = lng
        }
    }
}