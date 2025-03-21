package com.example.weatherapp.features.main.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class LocationViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var locationManager: LocationManager
    private var localPermissionGpsCode = 2
    private var fusedClient: FusedLocationProviderClient
    val locationState: MutableState<Location?> = mutableStateOf(null)

    private val context: Context
        get() = getApplication<Application>().applicationContext

    init {
        fusedClient = LocationServices.getFusedLocationProviderClient(context)
    }

    fun onStart(activity: Context) {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                getFreshLocation()
            } else {
                enableLocationServices(activity)
            }
        } else {
            if (activity is android.app.Activity) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    ),
                    localPermissionGpsCode
                )
            }
        }
    }

    private fun checkPermissions(): Boolean {
        return context.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED ||
                context.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    fun getFreshLocation() {
        fusedClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                locationState.value = location
            }
        }

        val locationRequest = LocationRequest.Builder(5000).apply {
            setPriority(Priority.PRIORITY_HIGH_ACCURACY)
        }.build()

        fusedClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    super.onLocationResult(p0)
                    val newLocation = p0.lastLocation
                    if (newLocation != null) {
                        locationState.value = newLocation
                    }
                }
            },
            Looper.getMainLooper()
        )
    }

    private fun enableLocationServices(context: Context) {
        Toast.makeText(
            context,
            "Turn on location",
            Toast.LENGTH_SHORT
        ).show()
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    private fun isLocationEnabled(): Boolean {
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    fun getDefaultLocation(): Location {
        return Location("").apply {
            latitude = 0.0
            longitude = 0.0
        }
    }
}