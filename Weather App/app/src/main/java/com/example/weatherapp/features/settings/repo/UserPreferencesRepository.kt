package com.example.weatherapp.features.settings.repo

import android.content.Context
import android.util.Log
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

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Suppress("PrivatePropertyName")
class UserPreferencesRepository(context: Context) {

    private val dataStore = context.dataStore

    private object PreferencesKeys {
        val LANGUAGE = stringPreferencesKey("language")
        val LOCATION = stringPreferencesKey("location")
        val TEMPERATURE = stringPreferencesKey("temperature")
        val WIND_SPEED = stringPreferencesKey("wind_speed")
        val LATITUDE = doublePreferencesKey("latitude")
        val LONGITUDE = doublePreferencesKey("longitude")
    }

    private val DEFAULT_LANGUAGE = "Default"
    private val DEFAULT_LOCATION = "GPS"
    private val DEFAULT_TEMPERATURE = "Celsius${AppConst.TEMP_DEGREE}C"
    private val DEFAULT_WIND_SPEED = "Meter/Sec"

    val languagePreference: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                Log.i("preference", "error: ${exception.message}")
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
                Log.i("preference", "error: ${exception.message}")
            }
        }
        .map { preferences ->
            preferences[PreferencesKeys.LOCATION] ?: DEFAULT_LOCATION
        }

    val temperaturePreference: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                Log.i("preference", "error: ${exception.message}")
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
                Log.i("preference", "error: ${exception.message}")
            }
        }
        .map { preferences ->
            preferences[PreferencesKeys.WIND_SPEED] ?: DEFAULT_WIND_SPEED
        }

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

    fun saveLanguagePreference(context: Context, languageCode: String) {
        val sharedPref = context.getSharedPreferences("AppLanguagePrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("language", languageCode)
            apply()
        }
    }

    fun loadLanguagePreference(context: Context): String {
        val sharedPref = context.getSharedPreferences("AppLanguagePrefs", Context.MODE_PRIVATE)
        return sharedPref.getString("language", "en") ?: "en"
    }

    fun saveUnitPreference(context: Context, unit: String) {
        val sharedPref = context.getSharedPreferences("AppLanguagePrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("unit", unit)
            apply()
        }
    }

    fun loadUnitPreference(context: Context): String {
        val sharedPref = context.getSharedPreferences("AppLanguagePrefs", Context.MODE_PRIVATE)
        return sharedPref.getString("unit", "metric") ?: "metric"
    }
}