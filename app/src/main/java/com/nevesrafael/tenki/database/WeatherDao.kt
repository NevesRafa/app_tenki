package com.nevesrafael.tenki.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.nevesrafael.tenki.model.WeatherData

@Dao
interface WeatherDao {

    @Query("SELECT * FROM WeatherData")
    fun searchAll(): List<WeatherData>

    @Query("SELECT * FROM WeatherData WHERE id = :id")
    fun searchById(id: Long): WeatherData?

    @Query("DELETE FROM WeatherData WHERE id = :id")
    fun remove(id: Long)

    @Update
    fun alter(weather: WeatherData)

    @Insert
    fun save(weather: WeatherData)
}