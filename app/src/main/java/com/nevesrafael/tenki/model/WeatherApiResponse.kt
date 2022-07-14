package com.nevesrafael.tenki.model

import com.google.gson.annotations.SerializedName

data class WeatherApiResponse(
    val list: List<WeatherDataApiResponse>
)

data class WeatherDataApiResponse(
    val main: WeatherTemperatureApiResponse,
    val weather: List<WeatherStateApiResponse>,
    @SerializedName("dt") val dateUnix: Long,
    @SerializedName("dt_txt") val date: String
)

data class WeatherTemperatureApiResponse(
    val temp: Double,
    @SerializedName("temp_min") val minTemperature: Double,
    @SerializedName("temp_max") val maxTemperature: Double
)

data class WeatherStateApiResponse(
    val main: String,
    val description: String,
    val icon: String
)