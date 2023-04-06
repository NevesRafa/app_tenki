package com.nevesrafael.tenki.data.model

import com.nevesrafael.tenki.data.remote.WeatherDataApiResponse

data class WeatherDetails(
    val temp: Int,
    val description: String,
    val weatherId: Long,
    val background: Int?,
    val weatherForecast: List<WeatherDataApiResponse>,
    val weatherIcon: String
)