@file:Suppress("DUPLICATE_LABEL_IN_WHEN")

package com.example.weatherapp.core

import androidx.compose.ui.graphics.Color

fun getAnmiBK(condition: String?): String {
    return when (condition?.lowercase()) {
        // English Conditions
        "clear", "clear sky", "sunny" -> "sunny_bk.json"

        // Arabic Conditions
        "صافٍ", "سماء صافية", "مشمس" -> "sunny_bk.json"

        // English Conditions
        "clouds", "few clouds", "scattered clouds", "broken clouds", "overcast clouds",
        "partly cloudy", "overcast" -> "clouds_bk.json"

        // Arabic Conditions
        "غائم", "غيوم قليلة", "غيوم متفرقة", "غيوم مكسورة", "غيوم كثيفة",
        "غائم جزئياً", "غيوم كاملة" -> "clouds_bk.json"

        // English Conditions
        "mist", "fog", "freezing fog", "haze", "smoke", "sand", "dust", "volcanic ash" -> "mist_bk.json"

        // Arabic Conditions
        "ضباب", "ضباب", "ضباب متجمد", "غيم", "دخان", "رمال", "غبار", "رماد بركاني" -> "mist_bk.json"

        // English Conditions
        "drizzle", "light intensity drizzle", "drizzle", "heavy intensity drizzle",
        "light intensity drizzle rain", "drizzle rain", "heavy intensity drizzle rain",
        "shower rain and drizzle", "heavy shower rain and drizzle",
        "light drizzle", "freezing drizzle" -> "rain_bk.json"

        // Arabic Conditions
        "رذاذ", "رذاذ خفيف الكثافة", "رذاذ", "رذاذ شديد الكثافة",
        "رذاذ مطر خفيف الكثافة", "رذاذ مطر", "رذاذ مطر شديد الكثافة",
        "زخات مطر ورذاذ", "زخات مطر ورذاذ كثيفة",
        "رذاذ خفيف", "رذاذ متجمد" -> "rain_bk.json"

        // English Conditions
        "rain", "light rain", "moderate rain", "heavy intensity rain", "very heavy rain",
        "extreme rain", "freezing rain", "light intensity shower rain", "shower rain",
        "heavy intensity shower rain", "ragged shower rain",
        "moderate rain", "heavy rain" -> "rain_bk.json"

        // Arabic Conditions
        "مطر", "مطر خفيف", "مطر معتدل", "مطر شديد الكثافة", "مطر غزير للغاية",
        "مطر متطرف", "مطر متجمد", "زخات مطر خفيفة الكثافة", "زخات مطر",
        "زخات مطر شديدة الكثافة", "زخات مطر متقطعة",
        "مطر معتدل", "مطر غزير" -> "rain_bk.json"

        // English Conditions
        "thunderstorm", "thunderstorm with light rain", "thunderstorm with rain",
        "thunderstorm with heavy rain", "light thunderstorm", "heavy thunderstorm",
        "ragged thunderstorm", "thunderstorm with light drizzle", "thunderstorm with drizzle",
        "thunderstorm with heavy drizzle", "patchy light rain with thunder" -> "thunderstorm_bk.json"

        // Arabic Conditions
        "عاصفة رعدية", "عاصفة رعدية مع مطر خفيف", "عاصفة رعدية مع مطر",
        "عاصفة رعدية مع مطر غزير", "عاصفة رعدية خفيفة", "عاصفة رعدية شديدة",
        "عاصفة رعدية متقطعة", "عاصفة رعدية مع رذاذ خفيف", "عاصفة رعدية مع رذاذ",
        "عاصفة رعدية مع رذاذ غزير", "مطر خفيف متقطع مع رعد" -> "thunderstorm_bk.json"

        // English Conditions
        "snow", "light snow", "heavy snow", "sleet", "light shower sleet",
        "shower sleet", "light rain and snow", "rain and snow", "light shower snow",
        "shower snow", "heavy shower snow", "blizzard", "heavy snow" -> "snow_bk.json"

        // Arabic Conditions
        "ثلج", "ثلج خفيف", "ثلج غزير", "ثلج مختلط", "زخات ثلج خفيفة مختلطة",
        "زخات ثلج مختلطة", "مطر وثلج خفيف", "مطر وثلج", "زخات ثلج خفيفة",
        "زخات ثلج", "زخات ثلج غزيرة", "عاصفة ثلجية", "ثلج غزير" -> "snow_bk.json"

        // English Conditions
        "tornado", "squall" -> "thunderstorm_bk.json"

        // Arabic Conditions
        "إعصار", "هبة" -> "thunderstorm_bk.json"

        else -> "rain_bk.json"
    }
}

fun getNavBK(condition: String?): Color {
    return when (condition?.lowercase()) {
        // English Conditions
        "clear", "clear sky", "sunny" -> AppColors.SunnyBK

        // Arabic Conditions
        "صافٍ", "سماء صافية", "مشمس" -> AppColors.SunnyBK

        // English Conditions
        "clouds", "few clouds", "scattered clouds", "broken clouds", "overcast clouds",
        "partly cloudy", "overcast" -> AppColors.CloudsBK

        // Arabic Conditions
        "غائم", "غيوم قليلة", "غيوم متفرقة", "غيوم مكسورة", "غيوم كثيفة",
        "غائم جزئياً", "غيوم كاملة" -> AppColors.CloudsBK

        // English Conditions
        "mist", "fog", "freezing fog", "haze", "smoke", "sand", "dust", "volcanic ash" -> AppColors.MistBK

        // Arabic Conditions
        "ضباب", "ضباب", "ضباب متجمد", "غيم", "دخان", "رمال", "غبار", "رماد بركاني" -> AppColors.MistBK

        // English Conditions
        "drizzle", "light intensity drizzle", "drizzle", "heavy intensity drizzle",
        "light intensity drizzle rain", "drizzle rain", "heavy intensity drizzle rain",
        "shower rain and drizzle", "heavy shower rain and drizzle",
        "light drizzle", "freezing drizzle" -> AppColors.RainyBK

        // Arabic Conditions
        "رذاذ", "رذاذ خفيف الكثافة", "رذاذ", "رذاذ شديد الكثافة",
        "رذاذ مطر خفيف الكثافة", "رذاذ مطر", "رذاذ مطر شديد الكثافة",
        "زخات مطر ورذاذ", "زخات مطر ورذاذ كثيفة",
        "رذاذ خفيف", "رذاذ متجمد" -> AppColors.RainyBK

        // English Conditions
        "rain", "light rain", "moderate rain", "heavy intensity rain", "very heavy rain",
        "extreme rain", "freezing rain", "light intensity shower rain", "shower rain",
        "heavy intensity shower rain", "ragged shower rain",
        "moderate rain", "heavy rain" -> AppColors.RainyBK

        // Arabic Conditions
        "مطر", "مطر خفيف", "مطر معتدل", "مطر شديد الكثافة", "مطر غزير للغاية",
        "مطر متطرف", "مطر متجمد", "زخات مطر خفيفة الكثافة", "زخات مطر",
        "زخات مطر شديدة الكثافة", "زخات مطر متقطعة",
        "مطر معتدل", "مطر غزير" -> AppColors.RainyBK

        // English Conditions
        "thunderstorm", "thunderstorm with light rain", "thunderstorm with rain",
        "thunderstorm with heavy rain", "light thunderstorm", "heavy thunderstorm",
        "ragged thunderstorm", "thunderstorm with light drizzle", "thunderstorm with drizzle",
        "thunderstorm with heavy drizzle", "patchy light rain with thunder" -> AppColors.ThunderstormBK

        // Arabic Conditions
        "عاصفة رعدية", "عاصفة رعدية مع مطر خفيف", "عاصفة رعدية مع مطر",
        "عاصفة رعدية مع مطر غزير", "عاصفة رعدية خفيفة", "عاصفة رعدية شديدة",
        "عاصفة رعدية متقطعة", "عاصفة رعدية مع رذاذ خفيف", "عاصفة رعدية مع رذاذ",
        "عاصفة رعدية مع رذاذ غزير", "مطر خفيف متقطع مع رعد" -> AppColors.ThunderstormBK

        // English Conditions
        "snow", "light snow", "heavy snow", "sleet", "light shower sleet",
        "shower sleet", "light rain and snow", "rain and snow", "light shower snow",
        "shower snow", "heavy shower snow", "blizzard", "heavy snow" -> AppColors.SnowBK

        // Arabic Conditions
        "ثلج", "ثلج خفيف", "ثلج غزير", "ثلج مختلط", "زخات ثلج خفيفة مختلطة",
        "زخات ثلج مختلطة", "مطر وثلج خفيف", "مطر وثلج", "زخات ثلج خفيفة",
        "زخات ثلج", "زخات ثلج غزيرة", "عاصفة ثلجية", "ثلج غزير" -> AppColors.SnowBK

        // English Conditions
        "tornado", "squall" -> AppColors.ThunderstormBK

        // Arabic Conditions
        "إعصار", "هبة" -> AppColors.ThunderstormBK

        else -> AppColors.RainyBK
    }
}