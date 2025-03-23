package com.example.weatherapp.features.main.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.core.AppColors
import com.example.weatherapp.core.Response
import com.example.weatherapp.core.getNavBK
import com.example.weatherapp.features.home.view.HomeView
import com.example.weatherapp.features.home.viewmodel.HomeViewModel
import com.example.weatherapp.features.setting.view.SettingView

@Composable
fun MainView(viewModel: HomeViewModel) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavBar(navController, viewModel) }
    ) { paddingValues ->
        Box(Modifier.padding(paddingValues)) {
            NavigationGraph(navController, viewModel)
        }
    }
}

@Composable
fun BottomNavBar(navController: NavHostController, viewModel: HomeViewModel) {
    val dataState by viewModel.weatherModelResponse.collectAsState()

    var baseBackgroundColor = Color.Gray
    if (dataState is Response.Success) {
        val weatherModel = (dataState as Response.Success).data
        baseBackgroundColor = getNavBK(weatherModel.weather[0].description)
    }
    val colors = listOf(
        baseBackgroundColor,
        Color(0xFF4CAF50),
        AppColors.SettingNav
    )

    val items = listOf(
        BottomNavItem("Home", Icons.Default.Home, "home"),
        BottomNavItem("Profile", Icons.Default.Person, "profile"),
        BottomNavItem("Settings", Icons.Default.Settings, "settings")
    )

    var selectedItem by remember { mutableIntStateOf(0) }
    val backgroundColor = colors[selectedItem]

    NavigationBar(containerColor = backgroundColor) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = Color.White
                    )
                },
                label = { Text(item.label, color = Color.White) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(item.route)
                }
            )
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController, viewModel: HomeViewModel) {
    NavHost(navController, startDestination = "home") {
        composable("home") { HomeView(viewModel) }
        composable("profile") { ScreenContent("Profile Screen") }
        composable("settings") { SettingView() }
    }
}

@Composable
fun ScreenContent(text: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text)
    }
}

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)