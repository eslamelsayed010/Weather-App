package com.example.weatherapp.features.favorite.model

import com.example.weatherapp.core.DataSource
import com.example.weatherapp.core.FakeDataSource
import com.example.weatherapp.features.home.model.Clouds
import com.example.weatherapp.features.home.model.Coord
import com.example.weatherapp.features.home.model.Main
import com.example.weatherapp.features.home.model.Sys
import com.example.weatherapp.features.home.model.Weather
import com.example.weatherapp.features.home.model.WeatherModel
import com.example.weatherapp.features.home.model.Wind
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class FavoriteRepoTest {
    private val favoriteModel = FavoriteModel(
        "City",
        "Country",
        1.0,
        2.0
    )
    private val newFavoriteModel = FavoriteModel(
        "New City",
        "New Country",
        3.0,
        4.0
    )

    private val weatherModel: WeatherModel = WeatherModel(
        coord = Coord(lat = 1.0, lon = 2.0),
        weather = listOf(
            Weather(
                id = 800,
                main = "Clear",
                description = "clear sky",
                icon = "01d"
            )
        ),
        base = "stations",
        main = Main(
            temp = 25.0,
            feelsLike = 26.0,
            tempMin = 23.0,
            tempMax = 27.0,
            pressure = 1012,
            seaLevel = 1012,
            groundLevel = 1010,
            humidity = 60,
            tempKf = 0.0
        ),
        visibility = 10000,
        wind = Wind(
            speed = 5.0,
            deg = 180,
            gust = 7.0
        ),
        clouds = Clouds(all = 0),
        dt = 1617192000,
        sys = Sys(
            type = 1,
            id = 123,
            country = "Country",
            sunrise = 1617170000,
            sunset = 1617215000
        ),
        timezone = 3600,
        id = 12345,
        name = "City",
        cod = 200
    )

    private lateinit var fakeLocalDataSource: DataSource
    private lateinit var fakeRemoteDataSource: DataSource
    private lateinit var repo: FavoriteRepo

    @Before
    fun setup() {
        fakeLocalDataSource = FakeDataSource(favoriteModel, weatherModel)
        fakeRemoteDataSource = FakeDataSource(favoriteModel, weatherModel)
        repo = FavoriteRepo.getInstance(fakeLocalDataSource, fakeRemoteDataSource)
    }

    @Test
    fun getCurrentWeather_getWeatherName_returnWeatherModel() = runTest {
        val response = repo.getCurrentWeather(1.0, 2.0, "metric", "en")

        response
            .collect {
                val data = it
                assertThat(data.name, `is`(weatherModel.name))
            }
    }

    @Test
    fun getFavorites_getCityName_returnFavoriteModel() = runTest {
        val response = repo.getFavorites().first()
        assertThat(response[0].city, `is`(favoriteModel.city))
    }

    @Test
    fun insertToFavorite_newItem_returnsCorrectSize() = runTest {
        val result = repo.insertToFavorite(newFavoriteModel)

        assertThat(result, `is`(2L))

        val favorites = repo.getFavorites().first()
        assertThat(favorites.size, `is`(2))
        assertThat(favorites.contains(newFavoriteModel), `is`(true))
    }

    @Test
    fun deleteFromFavorite_existingItem_returnsOne() = runTest {
        val result = repo.deleteFromFavorite(favoriteModel)

        assertThat(result, `is`(1))

        val favorites = repo.getFavorites().first()
        assertThat(favorites.isEmpty(), `is`(true))
    }
}