package com.nevesrafael.tenki.model

import retrofit2.http.GET

interface WeatherApi {

    @GET("data/2.5/forecast?q=Sorocaba&lang=pt_br&appid=87c2ecbeb52654589432513009c0bd19")
    suspend fun getWeatherForecast(): WeatherApiResponse

    @GET("data/2.5/weather?q=Sorocaba&lang=pt_br&appid=87c2ecbeb52654589432513009c0bd19")
    suspend fun getWeatherToday(): WeatherTodayApiResponse

}