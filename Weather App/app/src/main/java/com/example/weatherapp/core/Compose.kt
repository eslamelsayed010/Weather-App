package com.example.weatherapp.core

import androidx.compose.ui.graphics.Color

fun getThemeColor(condition: String?): Color {
    return when (condition) {
        "Clear", "Sunny" -> Color(0xFFFFA500)

        "Clouds", "Partly cloudy", "Overcast" -> Color(0xFFB0BEC5)

        "Mist", "Fog", "Freezing fog" -> Color(0xFF90CAF9)

        "Drizzle", "Patchy light drizzle", "Light drizzle", "Freezing drizzle", "Heavy freezing drizzle" -> Color(
            0xFF64B5F6
        )

        "Rain", "Patchy light rain", "Light rain", "Moderate rain at times", "Moderate rain",
        "Heavy rain at times", "Heavy rain", "Light freezing rain", "Moderate or heavy freezing rain" -> Color(
            0xFF1E88E5
        )

        "Thunderstorm", "Thundery outbreaks possible", "Patchy light rain with thunder", "Moderate or heavy rain with thunder" -> Color(
            0xFFFFA000
        )

        "Snow", "Patchy snow possible", "Light snow", "Moderate snow", "Heavy snow",
        "Patchy moderate snow", "Patchy heavy snow", "Ice pellets", "Blizzard", "Blowing snow" -> Color(
            0xFF90A4AE
        )

        else -> Color.Gray
    }
}

fun getGradientColors(condition: String?): List<Color> {
    return when (condition) {
        "Clear", "Sunny" -> listOf(
            Color(0xFFFFA500), // Orange
            Color(0xFFFFD700), // Golden Yellow
            Color(0xFFFFFACD)  // Light Sun Glow
        )

        "Clouds", "Partly cloudy", "Overcast" -> listOf(
            Color(0xFF607D8B), // Dark Grey-Blue
            Color(0xFF90A4AE), // Soft Grey
            Color(0xFFCFD8DC)  // Light Grey Mist
        )

        "Mist", "Fog", "Freezing fog" -> listOf(
            Color(0xFFB0BEC5), // Faint Grey
            Color(0xFFECEFF1), // Soft White
            Color(0xFFF5F5F5)  // Nearly White Fog
        )

        "Drizzle", "Light drizzle", "Freezing drizzle" -> listOf(
            Color(0xFF64B5F6), // Light Blue
            Color(0xFF2196F3), // Sky Blue
            Color(0xFF1565C0)  // Deep Rain Blue
        )

        "Rain", "Moderate rain", "Heavy rain" -> listOf(
            Color(0xFF1E88E5), // Dark Blue
            Color(0xFF0D47A1), // Deep Blue
            Color(0xFF263238)  // Dark Stormy Blue
        )

        "Thunderstorm", "Patchy light rain with thunder" -> listOf(
            Color(0xFFFF8C00), // Orange for Lightning
            Color(0xFF5D4037), // Stormy Brown
            Color(0xFF212121)  // Dark Grey-Black
        )

        "Snow", "Blizzard", "Heavy snow" -> listOf(
            Color(0xFFE3F2FD), // Light Ice Blue
            Color(0xFFB0BEC5), // Frosty Grey
            Color(0xFFF5F5F5)  // Soft White Snow
        )

        else -> listOf(
            Color(0xFF757575), // Neutral Grey
            Color(0xFF424242), // Dark Grey
            Color(0xFF212121)  // Almost Black
        )
    }
}

