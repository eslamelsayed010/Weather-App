package com.example.weatherapp.features.favorite.viewmodel

import com.example.weatherapp.features.favorite.model.FavoriteModel
import com.example.weatherapp.features.favorite.model.FavoriteRepo
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FavoriteViewModelTest {
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var repository: FavoriteRepo
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk(relaxed = true)
        viewModel = FavoriteViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun insertToFavorite_callRepositoryInsertToFavorite() = runTest {
        val favModel = FavoriteModel("City", "Country", 1.0, 2.0)

        viewModel.insertToFavorite(favModel)

        testDispatcher.scheduler.advanceUntilIdle()
        coVerify { repository.insertToFavorite(favModel) }
    }

    @Test
    fun deleteFromFavorite_callRepositoryDeleteFromFavorite() = runTest {
        val favModel = FavoriteModel("City", "Country", 1.0, 2.0)

        viewModel.deleteFromFavorite(favModel)

        testDispatcher.scheduler.advanceUntilIdle()
        coVerify { repository.deleteFromFavorite(favModel) }
    }

    @Test
    fun getLocalData_callRepositoryGetFavorites() = runTest {
        viewModel.getLocalData()

        testDispatcher.scheduler.advanceUntilIdle()
        coVerify { repository.getFavorites() }
    }
}