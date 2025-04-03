package com.example.weatherapp.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.weatherapp.features.favorite.model.FavoriteModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class DaoTest {
    private lateinit var dao: Dao
    private lateinit var database: AppDatabase

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()

        dao = database.dao()
    }

    @After
    fun tearDown() = database.close()

    @Test
    fun insertToFavorite_addNewFavModel() = runTest {
        val favModel = FavoriteModel("Country", "City", 1.0, 2.0)
        dao.insertToFavorite(favModel)

        val response = dao.getFavorites().first()
        assertThat(response[0], `is`(favModel))
    }

    @Test
    fun deleteFromFavorite_removeFavModel() = runTest {
        val favModel = FavoriteModel("Country", "City", 1.0, 2.0)
        dao.insertToFavorite(favModel)
        val response = dao.getFavorites().first()
        assertThat(response.isNotEmpty(), `is`(true))

        dao.deleteFromFavorite(favModel)
        val response2 = dao.getFavorites().first()
        assertThat(response2.isNotEmpty(), `is`(false))
    }
}