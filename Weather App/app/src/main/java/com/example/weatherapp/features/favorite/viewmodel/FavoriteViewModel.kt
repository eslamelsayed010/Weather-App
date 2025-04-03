package com.example.weatherapp.features.favorite.viewmodel

import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.features.favorite.model.FavoriteModel
import com.example.weatherapp.features.favorite.model.FavoriteRepo
import com.example.weatherapp.features.favorite.model.FavoriteResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class FavoriteViewModel(private val repo: FavoriteRepo) : ViewModel() {

    private var mutList =
        MutableStateFlow<FavoriteResponse>(FavoriteResponse.LoadingFavorite)
    val favoriteList = mutList.asStateFlow()

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()

    fun insertToFavorite(favoriteModel: FavoriteModel) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repo.insertToFavorite(favoriteModel)
                }
            } catch (e: Exception) {
                Log.e("TAG", e.toString())
            }
        }
    }

    fun deleteFromFavorite(favoriteModel: FavoriteModel) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repo.deleteFromFavorite(favoriteModel)
                }
            } catch (e: Exception) {
                Log.e("TAG", e.toString())
            }
        }
    }

    fun getLocalData() {
        viewModelScope.launch {
            try {
                val products = repo.getFavorites()
                products
                    .catch { ex ->
                        mutList.value = FavoriteResponse.FailureFavorite(ex)
                        _toastEvent.emit("Error From DAO: ${ex.message}")

                    }
                    .collect {
                        mutList.value = FavoriteResponse.SuccessFavorite(it)
                    }
            } catch (e: Exception) {
                Log.e("TAG", e.toString())
                mutList.value = FavoriteResponse.FailureFavorite(e)
                _toastEvent.emit("Error DAO: ${e.message}")
            }
        }
    }

    @Suppress("DEPRECATION")
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
class FavoriteViewModelFactory(private val repo: FavoriteRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteViewModel(repo) as T
    }
}