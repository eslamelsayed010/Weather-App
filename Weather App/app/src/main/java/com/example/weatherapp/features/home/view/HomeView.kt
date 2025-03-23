package com.example.weatherapp.features.home.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.core.AppColors
import com.example.weatherapp.core.AppConst
import com.example.weatherapp.core.CustomAnmiLoading
import com.example.weatherapp.core.CustomError
import com.example.weatherapp.core.DashedDivider
import com.example.weatherapp.core.LottieBackgroundBox
import com.example.weatherapp.core.Response
import com.example.weatherapp.core.getAnmiBK
import com.example.weatherapp.core.models.DailyForecast
import com.example.weatherapp.core.models.ThreeHourForecast
import com.example.weatherapp.core.models.WeatherModel
import com.example.weatherapp.features.home.viewmodel.HomeViewModel
import com.skydoves.landscapist.glide.GlideImage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeView(viewModel: HomeViewModel) {

    val scrollState = rememberScrollState()

    val current3HourForecast by viewModel.current3HourForecast.observeAsState(emptyList())
    val fiveDayForecast by viewModel.fiveDayForecast.observeAsState(emptyList())
    val dataState by viewModel.weatherModelResponse.collectAsState()

    when {
        dataState is Response.Loading
                && current3HourForecast.isEmpty()
                && fiveDayForecast.isEmpty() -> {
            CustomAnmiLoading()
        }

        dataState is Response.Failure -> {
            CustomError(viewModel)
        }

        dataState is Response.Success -> {
            val weatherModel = (dataState as Response.Success).data
            val anmiBK = getAnmiBK(weatherModel.weather[0].description)
            LottieBackgroundBox(anmiBK) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(15.dp)
                        .verticalScroll(scrollState),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    CustomTitle(weatherModel)
                    Spacer(Modifier.height(50.dp))
                    CustomCountryDegree(weatherModel)
                    Spacer(Modifier.height(10.dp))
                    CustomStatus(weatherModel)
                    Spacer(Modifier.height(20.dp))
                    CustomHourly(current3HourForecast)
                    Spacer(Modifier.height(20.dp))
                    Custom5NextDay(fiveDayForecast)
                }
            }
        }
    }
}

@Composable
private fun CustomTitle(weatherModel: WeatherModel) {
    val dateFormat = SimpleDateFormat("EEEE, d MMMM yyyy | h:mm a", Locale.getDefault())
    val currentDate = dateFormat.format(Date())
    var txtColor = Color.White
    if (weatherModel.weather[0].description == "Snow")
        txtColor = Color.Black
    Text(
        "${weatherModel.name}, ${weatherModel.sys.country}",
        fontSize = 25.sp,
        color = txtColor
    )
    Text(
        currentDate,
        fontSize = 18.sp,
        color = txtColor
    )
}

@SuppressLint("DefaultLocale")
@Composable
private fun CustomCountryDegree(weatherModel: WeatherModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    weatherModel.name,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .align(Alignment.CenterHorizontally),
                    fontSize = 30.sp,
                    color = Color.Black
                )
                Text(
                    String.format("%.2f%s", weatherModel.main.temp, AppConst.TEMP_DEGREE),
                    Modifier.align(Alignment.CenterHorizontally),
                    fontSize = 60.sp,
                    color = Color.Black
                )

            }
            GlideImage(
                imageModel = { AppConst.IMAGE_URL + weatherModel.weather[0].icon + AppConst.IMAGE_EXE },
                modifier = Modifier
                    .size(140.dp)
            )

        }
        Spacer(Modifier.height(5.dp))
        CustomMoreDevitalises(weatherModel)
    }
}

@Composable
private fun CustomMoreDevitalises(weatherModel: WeatherModel) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp + 60.dp
    Box(
        modifier = Modifier
            .shadow(10.dp, RoundedCornerShape(15.dp))
            .border(2.dp, Color.Transparent, RoundedCornerShape(15.dp))
            .background(Color.Transparent.copy(alpha = 0.3f), RoundedCornerShape(15.dp))
            .width(screenWidth / 2)
            .height(130.dp)
            .padding(8.dp)
    ) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        )
        {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomDetailItem(
                    type = "Wind Speed",
                    unit = "M/S",
                    measure = weatherModel.wind.speed
                )
                CustomDetailItem(
                    type = "Pressure",
                    unit = "HPA",
                    measure = weatherModel.main.pressure.toDouble()
                )
            }

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomDetailItem(
                    type = "Humidity",
                    unit = "%",
                    measure = weatherModel.main.humidity.toDouble()
                )
                CustomDetailItem(
                    type = "Clouds",
                    unit = "%",
                    measure = weatherModel.clouds.all.toDouble()
                )
            }
        }
    }

}

@Composable
private fun CustomDetailItem(
    type: String,
    unit: String,
    measure: Double
) {
    Column {
        Text(
            type,
            color = AppColors.Gray300,
            fontSize = 15.sp
        )
        Spacer(Modifier.height(3.dp))
        Row {
            Text(
                "$measure",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.alignByBaseline()
            )
            Spacer(Modifier.width(5.dp))
            Text(
                unit,
                color = AppColors.Gray300,
                fontSize = 13.sp,
                modifier = Modifier.alignByBaseline()
            )
        }
    }
}


@Composable
private fun CustomStatus(weatherModel: WeatherModel) {
    var txtColor = Color.White
    if (weatherModel.weather[0].description == "Mist")
        txtColor = Color.Black
    Text(
        weatherModel.weather[0].description,
        fontSize = 30.sp,
        color = txtColor
    )
    Spacer(Modifier.height(5.dp))
    Text(
        "H:${weatherModel.main.tempMax}${AppConst.TEMP_DEGREE} L:${weatherModel.main.tempMin}${AppConst.TEMP_DEGREE}",
        fontSize = 22.sp,
        color = Color.Gray
    )
}

@Composable
private fun CustomHourly(current3HourForecast: List<ThreeHourForecast>) {
    Column(
        Modifier.fillMaxWidth()
    ) {
        CustomTitleDivider("Hourly forecast")
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(current3HourForecast.size) { index ->
                CustomHourItem(current3HourForecast[index])
            }
        }

    }
}

@Composable
private fun CustomTitleDivider(txt: String) {
    Text(
        txt,
        fontSize = 25.sp,
        color = AppColors.Gray300
    )
    Spacer(Modifier.height(10.dp))
    DashedDivider()
    Spacer(Modifier.height(15.dp))
}

@Composable
fun CustomHourItem(threeHourForecast: ThreeHourForecast) {
    Box(
        modifier = Modifier
            .shadow(10.dp, RoundedCornerShape(50.dp))
            .border(2.dp, Color.Transparent, RoundedCornerShape(50.dp))
            .background(Color.Transparent.copy(alpha = 0.3f), RoundedCornerShape(50.dp))
            .height(200.dp)
            .padding(vertical = 15.dp, horizontal = 15.dp)
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                threeHourForecast.time,
                color = Color.White,
                fontSize = 20.sp
            )
            GlideImage(
                imageModel = { AppConst.IMAGE_URL + threeHourForecast.weatherIcon + AppConst.IMAGE_EXE },
                modifier = Modifier.size(60.dp)
            )
            Text(
                "${threeHourForecast.temperature}${AppConst.TEMP_DEGREE}",
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
private fun Custom5NextDay(fiveDayForecast: List<DailyForecast>) {
    CustomTitleDivider("Next 5 Days")

    for (element in fiveDayForecast) {
        Custom5NextDayItem(element)
        Spacer(Modifier.height(20.dp))
    }
}

@SuppressLint("DefaultLocale")
@Composable
private fun Custom5NextDayItem(dailyForecast: DailyForecast) {
    Box(
        modifier = Modifier
            .shadow(10.dp, RoundedCornerShape(15.dp))
            .border(2.dp, Color.Transparent, RoundedCornerShape(15.dp))
            .background(Color.Transparent.copy(alpha = 0.3f), RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .height(200.dp)
            .padding(15.dp)
    ) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    dailyForecast.date,
                    fontSize = 30.sp,
                    color = AppColors.Gray300
                )
                GlideImage(
                    imageModel = { AppConst.IMAGE_URL + dailyForecast.weatherIcon + AppConst.IMAGE_EXE },
                    modifier = Modifier
                        .height(100.dp)
                        .width(150.dp)
                )
            }
            Column {
                Text(
                    "H: ${dailyForecast.maxTemperature}${AppConst.TEMP_DEGREE} L: ${dailyForecast.minTemperature}${AppConst.TEMP_DEGREE}",
                    fontSize = 20.sp,
                    color = Color.Gray
                )
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        String.format("%.2f%s", dailyForecast.avgTemperature, AppConst.TEMP_DEGREE),
                        fontSize = 40.sp,
                        color = Color.White
                    )
                    Text(
                        dailyForecast.weatherDescription,
                        fontSize = 22.sp,
                        color = AppColors.Gray300
                    )
                }
            }
        }
    }
}