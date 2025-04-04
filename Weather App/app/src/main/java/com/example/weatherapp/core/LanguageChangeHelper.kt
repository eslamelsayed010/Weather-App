@file:Suppress("DEPRECATION")

package com.example.weatherapp.core

import android.annotation.SuppressLint
import android.app.Activity
import android.app.LocaleManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.example.weatherapp.MainActivity
import java.util.Locale

class LanguageChangeHelper {
    fun changeLanguage(context: Context, languageCode: String) {

        val effectiveLanguageCode = if (languageCode == "Default") {
            getDeviceDefaultLanguage()
            "en"
        } else {
            languageCode
        }

        val locale = Locale(effectiveLanguageCode)
        Locale.setDefault(locale)

        val resources = context.resources
        val configuration = resources.configuration
        configuration.setLocale(locale)

        context.createConfigurationContext(configuration)
        resources.updateConfiguration(configuration, resources.displayMetrics)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java).applicationLocales =
                LocaleList.forLanguageTags(effectiveLanguageCode)
        } else {
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(
                    effectiveLanguageCode
                )
            )
        }

        if (context is AppCompatActivity) {
            context.recreate()
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    fun getDeviceDefaultLanguage(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Locale.getDefault().language
        } else {
            Locale.getDefault().language
        }
    }


    fun getLanguageCode(context: Context): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java).applicationLocales[0]?.toLanguageTag()
                ?.split("-")?.first() ?: "en"
        } else {
            AppCompatDelegate.getApplicationLocales()[0]?.toLanguageTag()?.split("-")?.first()
                ?: "en"
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

    fun restartApp(context: Context) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("SKIP_SPLASH", true)
        }
        context.startActivity(intent)
        if (context is Activity) {
            context.finish()
        }
    }
}