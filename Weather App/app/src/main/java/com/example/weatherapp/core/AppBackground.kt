package com.example.weatherapp.core

import androidx.compose.ui.graphics.Color

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

fun getAnmiBK(condition: String?): String {
    return when (condition) {
        "Clear", "clear sky", "Sunny" -> "sunny_bk.json"

        "Clouds", "few clouds", "scattered clouds", "broken clouds", "overcast clouds",
        "Partly cloudy", "Overcast" -> "clouds_bk.json"

        "Mist", "Fog", "Freezing fog", "Haze", "smoke", "haze", "sand", "dust", "volcanic ash" -> "mist_bk.json"

        "Drizzle", "light intensity drizzle", "drizzle", "heavy intensity drizzle",
        "light intensity drizzle rain", "drizzle rain", "heavy intensity drizzle rain",
        "shower rain and drizzle", "heavy shower rain and drizzle",
        "Light drizzle", "Freezing drizzle" -> "rain_bk.json"

        "Rain", "light rain", "moderate rain", "heavy intensity rain", "very heavy rain",
        "extreme rain", "freezing rain", "light intensity shower rain", "shower rain",
        "heavy intensity shower rain", "ragged shower rain",
        "Moderate rain", "Heavy rain" -> "rain_bk.json"

        "Thunderstorm", "thunderstorm with light rain", "thunderstorm with rain",
        "thunderstorm with heavy rain", "light thunderstorm", "thunderstorm", "heavy thunderstorm",
        "ragged thunderstorm", "thunderstorm with light drizzle", "thunderstorm with drizzle",
        "thunderstorm with heavy drizzle", "Patchy light rain with thunder" -> "thunderstorm_bk.json"

        "Snow", "light snow", "snow", "heavy snow", "sleet", "light shower sleet",
        "shower sleet", "light rain and snow", "rain and snow", "light shower snow",
        "shower snow", "heavy shower snow", "Blizzard", "Heavy snow" -> "snow_bk.json"

        "tornado", "squall" -> "thunderstorm_bk.json"

        else -> "rain_bk.json"
    }
}

fun getNavBK(condition: String?): Color {
    return when (condition) {
        "Clear", "clear sky", "Sunny" -> AppColors.SunnyBK

        "Clouds", "few clouds", "scattered clouds", "broken clouds", "overcast clouds",
        "Partly cloudy", "Overcast" -> AppColors.CloudsBK

        "Mist", "Fog", "Freezing fog", "Haze", "smoke", "haze", "sand", "dust", "volcanic ash" -> AppColors.MistBK

        "Drizzle", "light intensity drizzle", "drizzle", "heavy intensity drizzle",
        "light intensity drizzle rain", "drizzle rain", "heavy intensity drizzle rain",
        "shower rain and drizzle", "heavy shower rain and drizzle",
        "Light drizzle", "Freezing drizzle" -> AppColors.RainyBK

        "Rain", "light rain", "moderate rain", "heavy intensity rain", "very heavy rain",
        "extreme rain", "freezing rain", "light intensity shower rain", "shower rain",
        "heavy intensity shower rain", "ragged shower rain",
        "Moderate rain", "Heavy rain" -> AppColors.RainyBK

        "Thunderstorm", "thunderstorm with light rain", "thunderstorm with rain",
        "thunderstorm with heavy rain", "light thunderstorm", "thunderstorm", "heavy thunderstorm",
        "ragged thunderstorm", "thunderstorm with light drizzle", "thunderstorm with drizzle",
        "thunderstorm with heavy drizzle", "Patchy light rain with thunder" -> AppColors.ThunderstormBK

        "Snow", "light snow", "snow", "heavy snow", "sleet", "light shower sleet",
        "shower sleet", "light rain and snow", "rain and snow", "light shower snow",
        "shower snow", "heavy shower snow", "Blizzard", "Heavy snow" -> AppColors.SnowBK

        "tornado", "squall" -> AppColors.ThunderstormBK

        else -> AppColors.Gray500
    }
}

