package com.nevesrafael.tenki.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/forecast")
    suspend fun getWeatherForecast(
        @Query("q") name: String,
        @Query("lang") language: String = "pt_br",
        @Query("appid") key: String = "a164dd5e11d56abc5b28c37155fc0e4e"
    ): WeatherApiResponse


    @GET("data/2.5/weather")
    suspend fun getWeatherToday(
        @Query("q") name: String,
        @Query("lang") language: String = "pt_br",
        @Query("appid") key: String = "a164dd5e11d56abc5b28c37155fc0e4e"
    ): WeatherTodayApiResponse

}




