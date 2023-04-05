package com.nevesrafael.tenki.domain

import com.nevesrafael.tenki.data.local.WeatherDao
import com.nevesrafael.tenki.data.model.WeatherDetails
import com.nevesrafael.tenki.data.remote.CityApi
import com.nevesrafael.tenki.data.remote.WeatherApi

class TenkiRepository(
    private val database: WeatherDao,
    private val cityApi: CityApi,
    private val weatherApi: WeatherApi
) {


    fun cityById(id: Long): WeatherDetails? {
        return database.searchById(id)
    }

    fun removeCityById(id: Long) {
        return database.remove(id)
    }

    fun saveCity(city: WeatherDetails) {
        return database.save(city)
    }
}