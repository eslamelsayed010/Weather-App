package com.example.weatherapp.features.favorite.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.weatherapp.R
import com.example.weatherapp.core.AppColors
import com.example.weatherapp.core.CustomAnmiLoading
import com.example.weatherapp.core.CustomError
import com.example.weatherapp.core.NavViewRoute
import com.example.weatherapp.features.favorite.model.FavoriteResponse
import com.example.weatherapp.features.favorite.viewmodel.FavoriteViewModel
import com.example.weatherapp.features.main.views.FavoriteItem

@Composable
fun FavoriteView(navController: NavHostController, favoriteViewModel: FavoriteViewModel) {
    val context = LocalContext.current

    favoriteViewModel.getLocalData()
    val dataState by (favoriteViewModel.favoriteList).collectAsState()

    Scaffold(containerColor = AppColors.BackgroundColor, floatingActionButton = {
        ExtendedFloatingActionButton(
            onClick = { navController.navigate(NavViewRoute.FAV_MAP) },
            icon = { Icon(Icons.Filled.Favorite, contentDescription = "Add icon") },
            text = { Text(stringResource(R.string.add_to_fav)) },
            containerColor = AppColors.PrimaryColor,
            contentColor = Color.White,
        )
    }) { innerPadding ->

        when (dataState) {
            is FavoriteResponse.FailureFavorite -> {
                CustomError(favoriteViewModel.toastEvent)
            }

            is FavoriteResponse.LoadingFavorite -> {
                CustomAnmiLoading()
            }

            is FavoriteResponse.SuccessFavorite -> {
                val data = (dataState as FavoriteResponse.SuccessFavorite).data
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier
                        .padding(innerPadding)
                        .background(AppColors.BackgroundColor)
                        .fillMaxSize()
                ) {
                    items(data.size) { index ->
                        FavoriteItem(
                            data[index],
                            onDelete = {
                                favoriteViewModel.deleteFromFavorite(data[index])
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.Deleted),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            )
                    }
                }
            }
        }
    }
}