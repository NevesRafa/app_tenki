package com.nevesrafael.tenki.domain

import com.nevesrafael.tenki.data.local.WeatherDao
import com.nevesrafael.tenki.data.model.CityDetails
import com.nevesrafael.tenki.data.model.WeatherDetails
import com.nevesrafael.tenki.data.remote.WeatherApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TenkiRepository(
    private val database: WeatherDao
) {

    val weatherApi = Retrofit.Builder() //cria o "Criador"
        .baseUrl("https://api.openweathermap.org/") // adiciona URL base
        .addConverterFactory(GsonConverterFactory.create()) // adiciona o cara que converte JSON pra objetos
        .build()
        .create(WeatherApi::class.java) // cria uma instancia da WeatherApi

    fun cityById(id: Long): CityDetails? {
        return database.searchById(id)
    }

    fun removeCityById(id: Long) {
        return database.remove(id)
    }

    fun saveCity(city: CityDetails) {
        return database.save(city)
    }

    suspend fun callWeatherToday(cityName: String): WeatherDetails {
        val weatherToday = weatherApi.getWeatherToday(cityName)
        val weatherForecast = weatherApi.getWeatherForecast(cityName)

        return WeatherMapper.map(weatherToday, weatherForecast)
    }


}