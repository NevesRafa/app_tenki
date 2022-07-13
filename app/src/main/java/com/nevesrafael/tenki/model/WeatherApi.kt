package com.nevesrafael.tenki.model

import retrofit2.http.GET

interface WeatherApi {

    @GET("data/2.5/forecast?q=Sorocaba&lang=pt_br&appid=<CHAVE DE API>")
    suspend fun getWeatherForecast(): WeatherApiResponse

    @GET("data/2.5/weather?q=Sorocaba&lang=pt_br&appid=<CHAVE DE API>")
    suspend fun getWeatherToday(): WeatherTodayApiResponse

}