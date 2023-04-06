package com.nevesrafael.tenki.data.model

import com.nevesrafael.tenki.data.remote.WeatherDataApiResponse

data class WeatherDetails(
    val weather: String?,
    val temp: Int,
    val description: String,
    val weatherId: Long,
    val background: Int?,
    val weatherForecast: List<WeatherDataApiResponse>
)