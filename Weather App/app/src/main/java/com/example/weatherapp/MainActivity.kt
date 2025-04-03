@file:Suppress("OVERRIDE_DEPRECATION")

package com.example.weatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.core.AppColors
import com.example.weatherapp.core.LanguageChangeHelper
import com.example.weatherapp.features.favorite.model.FavoriteRepo
import com.example.weatherapp.features.favorite.viewmodel.FavoriteViewModel
import com.example.weatherapp.features.favorite.viewmodel.FavoriteViewModelFactory
import com.example.weatherapp.features.main.viewmodel.LocationViewModel
import com.example.weatherapp.features.main.views.SetupHomeLocation
import com.example.weatherapp.features.notification.model.NotificationRepo
import com.example.weatherapp.features.notification.viewmodel.NotificationViewModel
import com.example.weatherapp.features.notification.viewmodel.NotificationViewModelFactory
import com.example.weatherapp.features.settings.repo.UserPreferencesRepository
import com.example.weatherapp.features.settings.viewmodel.SettingsViewModel
import com.example.weatherapp.features.settings.viewmodel.SettingsViewModelFactory
import com.example.weatherapp.local.AppDatabase
import com.example.weatherapp.local.LocalDataSource

@Suppress("DEPRECATION")
class MainActivity : ComponentActivity() {

    private lateinit var locationViewModel: LocationViewModel
    private var localPermissionGpsCode = 2
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.i("isGranted", "if : ")
        } else {
            Log.i("isGranted", "else: ")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkNotificationPermission()

        window.statusBarColor = AppColors.BackgroundColor.toArgb()

        locationViewModel = ViewModelProvider(this)[LocationViewModel::class.java]
        val settingsViewModel =
            ViewModelProvider(
                this,
                SettingsViewModelFactory(
                    UserPreferencesRepository(this),
                    this
                )
            )[SettingsViewModel::class.java]

        val notificationViewModel = ViewModelProvider(
            this,
            NotificationViewModelFactory(
                NotificationRepo.getInstance(
                    LocalDataSource(
                        AppDatabase
                            .getInstance(this)
                            .dao()
                    )
                )
            )
        )[NotificationViewModel::class.java]

        val favoriteViewModel = ViewModelProvider(
            this,
            FavoriteViewModelFactory(
                FavoriteRepo.getInstance(
                    LocalDataSource(
                        AppDatabase
                            .getInstance(this)
                            .dao()
                    )
                )
            )
        )[FavoriteViewModel::class.java]

        val languageHelper = LanguageChangeHelper()
        val savedLanguage = languageHelper.loadLanguagePreference(this)
        val savedUnit = languageHelper.loadUnitPreference(this)
        languageHelper.changeLanguage(this, savedLanguage)

        setContent {
            val location =
                locationViewModel.locationState.value ?: locationViewModel.getDefaultLocation()
            SetupHomeLocation(
                settingsViewModel,
                notificationViewModel,
                favoriteViewModel,
                location,
                locationViewModel,
                this,
                savedUnit,
                savedLanguage
            )
        }
    }

    override fun onStart() {
        super.onStart()
        locationViewModel.onStart(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == localPermissionGpsCode)
            if (grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            )
                locationViewModel.getFreshLocation()

    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}