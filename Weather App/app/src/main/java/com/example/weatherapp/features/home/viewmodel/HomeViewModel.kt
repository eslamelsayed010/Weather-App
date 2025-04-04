package com.example.weatherapp.features.home.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.core.AppConst
import com.example.weatherapp.features.home.model.HomeResponse
import com.example.weatherapp.features.home.model.DailyForecast
import com.example.weatherapp.features.home.model.ThreeHourForecast
import com.example.weatherapp.features.home.model.WeatherRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@SuppressLint("LogNotTimber")
class HomeViewModel(
    private val repo: WeatherRepo,
    var lat: Double,
    var lon: Double,
    var unit: String,
    var lang: String,
) : ViewModel() {
    private var mutList = MutableStateFlow<HomeResponse>(HomeResponse.LoadingHome)
    val weatherModelResponse = mutList.asStateFlow()

    var current3HourForecast: MutableLiveData<List<ThreeHourForecast>> = MutableLiveData()
    var fiveDayForecast: MutableLiveData<List<DailyForecast>> = MutableLiveData()

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()

    init {
        refreshWeatherData()
    }

    fun refreshWeatherData() {
        getWeatherForecast()
        getCurrentWeather()
    }


    private fun getWeatherForecast() {
        viewModelScope.launch {
            try {
                val forecastData = withContext(Dispatchers.IO) {
                    forecastUrl()
                }

                current3HourForecast.postValue(getTodayThreeHourForecast(forecastData))
                fiveDayForecast.postValue(getFiveDayForecast(forecastData))

            } catch (e: Exception) {
                Log.e("TAG", e.toString())
                mutList.value = HomeResponse.FailureHome(e)
                _toastEvent.emit("Error From API: ${e.message}")
                Log.i("TAG", "Error From API: ${e.message}")
            }
        }
    }

    private fun getCurrentWeather() {
        viewModelScope.launch {
            try {
                val currentWeather = repo.getCurrentWeather(lat, lon, unit, lang)
                currentWeather
                    .catch { ex ->
                        mutList.value = HomeResponse.FailureHome(ex)
                        _toastEvent.emit("Error From Response: ${ex.message}")
                        Log.i("TAG", "Error From Response : ${ex.message}")
                    }
                    .collect {
                        mutList.value = HomeResponse.SuccessHome(it)
                        Log.i("TAG", mutList.value.toString())
                    }
            } catch (e: Exception) {
                Log.e("TAG", e.toString())
                mutList.value = HomeResponse.FailureHome(e)
                _toastEvent.emit("Error From API: ${e.message}")
                Log.i("TAG", "Error From API: ${e.message}")
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
                Log.d("WeatherDebug", "Raw temperature from API: ${main.getDouble("temp")}")
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

    private fun forecastUrl(): JSONObject {
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
}

@Suppress("UNCHECKED_CAST")
class HomeFactory(
    private val repo: WeatherRepo,
    var lat: Double,
    var lon: Double,
    var unit: String,
    var lang: String,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(repo, lat, lon, unit, lang) as T
    }
}