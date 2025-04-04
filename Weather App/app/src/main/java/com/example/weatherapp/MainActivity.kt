@file:Suppress("OVERRIDE_DEPRECATION")

package com.example.weatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.weatherapp.core.AppColors
import com.example.weatherapp.core.LanguageChangeHelper
import com.example.weatherapp.core.NetworkConnectivityMonitor
import com.example.weatherapp.data.local.AppDatabase
import com.example.weatherapp.data.local.LocalDataSource
import com.example.weatherapp.data.network.RemoteDataSource
import com.example.weatherapp.data.network.RetrofitHelper
import com.example.weatherapp.features.favorite.model.FavoriteRepo
import com.example.weatherapp.features.favorite.viewmodel.FavoriteViewModel
import com.example.weatherapp.features.favorite.viewmodel.FavoriteViewModelFactory
import com.example.weatherapp.features.main.viewmodel.LocationViewModel
import com.example.weatherapp.features.main.views.NetworkAlertDialog
import com.example.weatherapp.features.main.views.SetupHomeLocation
import com.example.weatherapp.features.notification.model.NotificationRepo
import com.example.weatherapp.features.notification.viewmodel.NotificationViewModel
import com.example.weatherapp.features.notification.viewmodel.NotificationViewModelFactory
import com.example.weatherapp.features.settings.repo.UserPreferencesRepository
import com.example.weatherapp.features.settings.viewmodel.SettingsViewModel
import com.example.weatherapp.features.settings.viewmodel.SettingsViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@Suppress("DEPRECATION")
class MainActivity : ComponentActivity() {

    private var keepSplashScreen = true
    private lateinit var locationViewModel: LocationViewModel
    private var localPermissionGpsCode = 2
    private lateinit var networkConnectivityMonitor: NetworkConnectivityMonitor

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Timber.tag("isGranted").i("if : ")
        } else {
            Timber.tag("isGranted").i("else: ")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkNotificationPermission()

        window.statusBarColor = AppColors.BackgroundColor.toArgb()

        // Initialize network connectivity monitor
        networkConnectivityMonitor = NetworkConnectivityMonitor(this)
        networkConnectivityMonitor.startMonitoring()

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
                    RemoteDataSource(RetrofitHelper),
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

        val skipSplash = intent.getBooleanExtra("SKIP_SPLASH", false)

        if (!skipSplash) {
            installSplashScreen().apply {
                setKeepOnScreenCondition { keepSplashScreen }
            }
            lifecycleScope.launch {
                delay(3000L)
                keepSplashScreen = false
            }
        } else
            keepSplashScreen = false

        setContent {
            val location =
                locationViewModel.locationState.value ?: locationViewModel.getDefaultLocation()

            // Observe network connectivity state
            val isNetworkConnected = networkConnectivityMonitor.isConnected.collectAsState()
            val showNetworkDialog = remember { mutableStateOf(false) }

            // Update dialog visibility when network state changes
            LaunchedEffect(isNetworkConnected.value) {
                showNetworkDialog.value = !isNetworkConnected.value
            }

            // Show alert dialog if network is disconnected
            if (showNetworkDialog.value) {
                NetworkAlertDialog(
                    onDismiss = {
                        showNetworkDialog.value = false
                    },
                    onConfirm = {
                        // Retry connection or refresh data
                        showNetworkDialog.value = false
                    }
                )
            }

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
        networkConnectivityMonitor.startMonitoring()
    }

    override fun onStop() {
        super.onStop()
        networkConnectivityMonitor.stopMonitoring()
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