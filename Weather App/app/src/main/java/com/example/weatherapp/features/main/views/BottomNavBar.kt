package com.example.weatherapp.features.main.views

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.weatherapp.R
import com.example.weatherapp.core.AppColors
import com.example.weatherapp.core.NavViewRoute
import com.example.weatherapp.core.Response
import com.example.weatherapp.core.getNavBK
import com.example.weatherapp.features.home.viewmodel.HomeViewModel
import com.example.weatherapp.features.main.model.BottomNavItem

@Composable
fun BottomNavBar(navController: NavHostController, viewModel: HomeViewModel) {
    val context = LocalContext.current
    val dataState by viewModel.weatherModelResponse.collectAsState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var baseBackgroundColor = Color.Gray
    if (dataState is Response.Success) {
        val weatherModel = (dataState as Response.Success).data
        baseBackgroundColor = getNavBK(weatherModel.weather[0].description)
    }

    val items = listOf(
        BottomNavItem(context.getString(R.string.home), Icons.Default.Home, NavViewRoute.HOME),
        BottomNavItem(
            context.getString(R.string.favorite),
            Icons.Default.Favorite,
            NavViewRoute.FAVORITE
        ),
        BottomNavItem(
            context.getString(R.string.alert),
            Icons.Default.Notifications,
            NavViewRoute.ALERT
        ),
        BottomNavItem(
            context.getString(R.string.settings),
            Icons.Default.Settings,
            NavViewRoute.SETTINGS
        )
    )

    val selectedItem = items.indexOfFirst { it.route == currentRoute }.takeIf { it >= 0 } ?: 0

    val colors = listOf(
        baseBackgroundColor,
        AppColors.BackgroundColor,
        AppColors.BackgroundColor,
        AppColors.BackgroundColor
    )

    val backgroundColor = colors[selectedItem]

    NavigationBar(containerColor = backgroundColor) {
        items.forEachIndexed { _, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (currentRoute == item.route) AppColors.PrimaryColor else Color.White
                    )
                },
                label = {
                    Text(
                        item.label,
                        color = if (currentRoute == item.route) AppColors.PrimaryColor  else Color.White
                    )
                },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
//                colors = NavigationBarItemDefaults.colors(
//                    selectedIconColor = Color.Yellow,
//                    selectedTextColor = Color.Yellow,
//                    indicatorColor = backgroundColor.copy(alpha = 0.5f)
//                )
            )
        }
    }
}