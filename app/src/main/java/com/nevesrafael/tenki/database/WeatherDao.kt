package com.nevesrafael.tenki.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import com.nevesrafael.tenki.model.WeatherData

@Dao
interface WeatherDao {

    @Query("SELECT * FROM WeatherData")
    fun searchAll(): List<WeatherData>

    @Query("SELECT * FROM WeatherData WHERE id = :id")
    fun searchId(id: Int): WeatherData?

    @Delete
    fun remove(weather: WeatherData)

    @Update
    fun alter(weather: WeatherData)
}