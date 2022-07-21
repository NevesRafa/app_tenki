package com.nevesrafael.tenki.model

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/forecast")
    suspend fun getWeatherForecast(
        @Query("lat") lat: Long,
        @Query("lon") lon: Long,
        @Query("lang") language: String = "pt_br",
        @Query("appid") key: String = "87c2ecbeb52654589432513009c0bd19"
    ): WeatherApiResponse


    @GET("data/2.5/weather")
    suspend fun getWeatherToday(
        @Query("lat") lat: Long,
        @Query("lon") lon: Long,
        @Query("lang") language: String = "pt_br",
        @Query("appid") key: String = "87c2ecbeb52654589432513009c0bd19"
    ): WeatherTodayApiResponse

}


//lat=-23.5003451&lon=-47.4582864
// q = cidade
//lang = linguagem
//appid = chave da api

