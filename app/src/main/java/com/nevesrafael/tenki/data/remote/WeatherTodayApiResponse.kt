package com.nevesrafael.tenki.data.remote

data class WeatherTodayApiResponse(
    val main: WeatherTemperatureApiResponse,
    val weather: List<WeatherStateApiResponse>,
    val id: Long,
    val name: String,
    val sunrise: Long,
    val sunset: Long
)