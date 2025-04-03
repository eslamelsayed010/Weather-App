package com.example.weatherapp.data.network

import com.example.weatherapp.core.AppConst
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private var retrofit = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(AppConst.BASE_URL)
        .build()

    var retrofitService: WeatherServices = retrofit
        .create(WeatherServices::class.java)
}