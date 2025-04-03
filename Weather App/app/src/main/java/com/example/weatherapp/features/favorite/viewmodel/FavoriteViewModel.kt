package com.example.weatherapp.features.favorite.viewmodel

import android.content.Context
import android.location.Geocoder
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.core.AppConst
import com.example.weatherapp.features.favorite.model.FavoriteModel
import com.example.weatherapp.features.favorite.model.FavoriteRepo
import com.example.weatherapp.features.favorite.model.FavoriteResponse
import com.example.weatherapp.features.home.model.DailyForecast
import com.example.weatherapp.features.home.model.ThreeHourForecast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import timber.log.Timber
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class FavoriteViewModel(private val repo: FavoriteRepo) : ViewModel() {

    private var mutListLocal =
        MutableStateFlow<FavoriteResponse>(FavoriteResponse.LoadingFavorite)
    val favoriteList = mutListLocal.asStateFlow()

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()

    private var mutList =
        MutableStateFlow<FavoriteResponse>(FavoriteResponse.LoadingSelectedFavorite)
    val weatherModelResponse = mutList.asStateFlow()

    var current3HourForecast: MutableLiveData<List<ThreeHourForecast>> = MutableLiveData()
    var fiveDayForecast: MutableLiveData<List<DailyForecast>> = MutableLiveData()

    var unit = "metric"
    var lang = "en"

    fun refreshWeatherData(
        lat: Double,
        lon: Double,
    ) {
        getWeatherForecast(lat, lon)
        getCurrentWeather(lat, lon)
    }

    private fun getWeatherForecast(
        lat: Double,
        lon: Double,
    ) {
        viewModelScope.launch {
            try {
                val forecastData = withContext(Dispatchers.IO) {
                    forecastUrl(lat, lon)
                }

                current3HourForecast.postValue(getTodayThreeHourForecast(forecastData))
                fiveDayForecast.postValue(getFiveDayForecast(forecastData))

            } catch (e: Exception) {
                Timber.tag("TAG").e(e.toString())
                mutList.value = FavoriteResponse.FailureSelectedFavorite(e)
                _toastEvent.emit("Error From API: ${e.message}")
                Timber.tag("TAG").i("Error From API: ${e.message}")
            }
        }
    }

    private fun getCurrentWeather(
        lat: Double,
        lon: Double,
    ) {
        viewModelScope.launch {
            try {
                val response = repo.getCurrentWeather(lat, lon, unit, lang)
                response
                    .catch { ex ->
                        mutList.value = FavoriteResponse.FailureSelectedFavorite(ex)
                        _toastEvent.emit("Error From Response: ${ex.message}")
                        Timber.tag("TAG").i("Error From Response : ${ex.message}")
                    }
                    .collect {
                        mutList.value = FavoriteResponse.SuccessSelectedFavorite(it)
                        Timber.tag("TAG").i(mutList.value.toString())
                    }
            } catch (e: Exception) {
                Timber.tag("TAG").e(e.toString())
                mutList.value = FavoriteResponse.FailureSelectedFavorite(e)
                _toastEvent.emit("Error From API: ${e.message}")
                Timber.tag("TAG").i("Error From API: ${e.message}")
            }
        }
    }

    private fun getTodayThreeHourForecast(forecastData: JSONObject): List<ThreeHourForecast> {
        val forecastList = forecastData.getJSONArray("list")
        val result = mutableListOf<ThreeHourForecast>()

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val today = sdf.format(Date())

        for (i in 0 until forecastList.length()) {
            val forecast = forecastList.getJSONObject(i)
            val dt = forecast.getLong("dt") * 1000
            val date = sdf.format(Date(dt))

            if (date == today) {
                val time = SimpleDateFormat("HH a", Locale.getDefault()).format(Date(dt))
                val main = forecast.getJSONObject("main")
                Timber.tag("WeatherDebug").d("Raw temperature from API: ${main.getDouble("temp")}")
                val weather = forecast.getJSONArray("weather").getJSONObject(0)

                result.add(
                    ThreeHourForecast(
                        time = time,
                        temperature = main.getDouble("temp"),
                        weatherDescription = weather.getString("description"),
                        weatherIcon = weather.getString("icon")
                    )
                )
            }
        }

        return result
    }

    private fun getFiveDayForecast(forecastData: JSONObject): List<DailyForecast> {
        val forecastList = forecastData.getJSONArray("list")
        val result = mutableListOf<DailyForecast>()

        val dailyMap = mutableMapOf<String, MutableList<JSONObject>>()

        for (i in 0 until forecastList.length()) {
            val forecast = forecastList.getJSONObject(i)
            val dt = forecast.getLong("dt") * 1000
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(dt))

            if (!dailyMap.containsKey(date)) {
                dailyMap[date] = mutableListOf()
            }

            dailyMap[date]?.add(forecast)
        }

        val calendar = Calendar.getInstance()
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)

        dailyMap.forEach { (date, forecasts) ->
            if (date != today) {

                var sumTemp = 0.0
                var minTemp = Double.MAX_VALUE
                var maxTemp = Double.MIN_VALUE
                var mainWeatherDescription = ""
                var mainWeatherIcon = ""

                val weatherFrequency = mutableMapOf<String, Int>()

                forecasts.forEach { forecast ->
                    val main = forecast.getJSONObject("main")
                    val temp = main.getDouble("temp")
                    sumTemp += temp
                    minTemp = minOf(minTemp, main.getDouble("temp_min"))
                    maxTemp = maxOf(maxTemp, main.getDouble("temp_max"))

                    val weather = forecast.getJSONArray("weather").getJSONObject(0)
                    val weatherId = weather.getString("id")
                    weatherFrequency[weatherId] = (weatherFrequency[weatherId] ?: 0) + 1

                    if (weatherFrequency[weatherId] == (weatherFrequency.values.maxOrNull() ?: 0)) {
                        mainWeatherDescription = weather.getString("description")
                        mainWeatherIcon = weather.getString("icon")
                    }
                }

                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val displayFormat = SimpleDateFormat("EEE, MMM d", Locale.getDefault())
                val displayDate = try {
                    val parsedDate = dateFormat.parse(date)
                    displayFormat.format(parsedDate ?: Date())
                } catch (e: Exception) {
                    date
                }

                result.add(
                    DailyForecast(
                        date = displayDate,
                        avgTemperature = sumTemp / forecasts.size,
                        minTemperature = minTemp,
                        maxTemperature = maxTemp,
                        weatherDescription = mainWeatherDescription,
                        weatherIcon = mainWeatherIcon
                    )
                )
            }
        }

        return result.sortedBy {
            SimpleDateFormat("EEE, MMM d", Locale.getDefault()).parse(it.date)?.time ?: 0
        }
    }

    private fun forecastUrl(
        lat: Double,
        lon: Double,
    ): JSONObject {
        val url =
            URL("https://api.openweathermap.org/data/2.5/forecast?lat=$lat&lon=$lon&appid=${AppConst.API_KEY}&units=$unit&lang=$lang")
        val response = url.readText()
        return JSONObject(response)
    }

    fun getCurrentFormattedDate(): String {
        return try {
            val dateFormat = SimpleDateFormat("EEEE, d MMMM yyyy | h:mm:ss a", Locale.getDefault())
            dateFormat.format(Date())
        } catch (e: Exception) {
            "Date Unavailable"
        }
    }

    fun insertToFavorite(favoriteModel: FavoriteModel) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repo.insertToFavorite(favoriteModel)
                }
            } catch (e: Exception) {
                Timber.tag("TAG").e(e.toString())
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
                Timber.tag("TAG").e(e.toString())
            }
        }
    }

    fun getLocalData() {
        viewModelScope.launch {
            try {
                val listFlow = repo.getFavorites()
                listFlow
                    .catch { ex ->
                        mutListLocal.value = FavoriteResponse.FailureFavorite(ex)
                        _toastEvent.emit("Error From DAO: ${ex.message}")

                    }
                    .collect {
                        mutListLocal.value = FavoriteResponse.SuccessFavorite(it)
                    }
            } catch (e: Exception) {
                Timber.tag("TAG").e(e.toString())
                mutListLocal.value = FavoriteResponse.FailureFavorite(e)
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