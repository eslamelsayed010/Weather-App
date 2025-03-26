package com.example.weatherapp.core

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ArabicDateLocalizer {

    private val arabicLocale = Locale("ar")

    fun localizeDate(date: String, locale: Locale = Locale.getDefault()): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", locale)

        val displayFormat = SimpleDateFormat("EEE, MMM d", locale)

        return try {
            val parsedDate = dateFormat.parse(date) ?: Date()
            displayFormat.format(parsedDate)
        } catch (e: Exception) {
            date
        }
    }

    fun localizeFullDate(date: String, locale: Locale = Locale.getDefault()): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", locale)

        val fullDisplayFormat = SimpleDateFormat("EEEE، d MMMM yyyy", locale)

        return try {
            val parsedDate = dateFormat.parse(date) ?: Date()
            fullDisplayFormat.format(parsedDate)
        } catch (e: Exception) {
            date
        }
    }

    fun convertToArabicNumerals(input: String): String {
        val arabicNumerals = charArrayOf('٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩')
        val builder = StringBuilder()

        for (char in input) {
            if (char in '0'..'9') {
                builder.append(arabicNumerals[char.toString().toInt()])
            } else {
                builder.append(char)
            }
        }

        return builder.toString()
    }
}