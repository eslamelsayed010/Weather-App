package com.example.weatherapp.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.weatherapp.features.favorite.model.FavoriteModel
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class LocalDataSourceTest {
    private lateinit var dao: Dao
    private lateinit var database: AppDatabase
    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries()
            .build()

        dao = database.dao()
        localDataSource = LocalDataSource(dao)
    }

    @After
    fun tearDown() = database.close()

    @Test
    fun insertToFavorite_addFavItemAndCheckListNotEmptyList() = runTest {
        val favMode = FavoriteModel("city", "country", 1.0, 2.0)
        localDataSource.insertToFavorite(favMode)

        val response = localDataSource.getFavorites().first()
        assertTrue(response.isNotEmpty())
    }

    @Test
    fun deleteFromFavorite_checkListEmptyList() = runTest {
        val favModel = FavoriteModel("city", "country", 1.0, 2.0)
        localDataSource.insertToFavorite(favModel)
        val response = localDataSource.getFavorites().first()
        assertTrue(response.isNotEmpty())

        localDataSource.deleteFromFavorite(favModel)
        val response2 = localDataSource.getFavorites().first()
        assertTrue(response2.isEmpty())
    }
}