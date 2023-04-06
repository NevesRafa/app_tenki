package com.nevesrafael.tenki.domain

import com.nevesrafael.tenki.R
import com.nevesrafael.tenki.TemperatureFormatter
import com.nevesrafael.tenki.data.model.WeatherDetails
import com.nevesrafael.tenki.data.remote.WeatherApiResponse
import com.nevesrafael.tenki.data.remote.WeatherTodayApiResponse
import java.util.*

object WeatherMapper {

    fun map(
        weatherToday: WeatherTodayApiResponse,
        weatherForecast: WeatherApiResponse
    ): WeatherDetails {

        val weather = weatherToday.weather.firstOrNull()?.main

        val temp = TemperatureFormatter.kelvinToCelsius(weatherToday.main.temp)

        val weatherId = weatherToday.id

        val description = weatherToday.weather.first().description.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        }

        return WeatherDetails(
            weather = weather,
            temp = temp,
            description = description,
            weatherId = weatherId,
            background = changeBackgroundImage(weather),
            weatherForecast = weatherForecast.list
        )
    }

    private fun changeBackgroundImage(weather: String?): Int? {

        val background = when (weather) {
            "11d" -> R.drawable.thunderstrorm_day
            "11n" -> R.drawable.thunderstrorm_night
            "09d", "10d" -> R.drawable.drizzle_rain_day
            "09n", "10n" -> R.drawable.drizzle_rain_night
            "13d" -> R.drawable.snow_day
            "13n" -> R.drawable.snow_night
            "50d", "50n" -> R.drawable.atmosphere
            "01d" -> R.drawable.clear_day
            "01n" -> R.drawable.clear_night
            "02d", "03d", "04d" -> R.drawable.clouds_day
            "02n", "03n", "04n" -> R.drawable.clouds_night
            else -> null
        }
        return background
    }
}
