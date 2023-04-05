package com.nevesrafael.tenki.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/forecast")
    suspend fun getWeatherForecast(
        @Query("q") name: String,
        @Query("lang") language: String = "pt_br",
        @Query("appid") key: String = "87c2ecbeb52654589432513009c0bd19"
    ): WeatherApiResponse


    @GET("data/2.5/weather")
    suspend fun getWeatherToday(
        @Query("q") name: String,
        @Query("lang") language: String = "pt_br",
        @Query("appid") key: String = "87c2ecbeb52654589432513009c0bd19"
    ): WeatherTodayApiResponse

}




