package com.nevesrafael.tenki.model

data class WeatherTodayApiResponse(
    val main: WeatherTemperatureApiResponse,
    val weather: List<WeatherStateApiResponse>,
    val id: Long,
    val name: String
)