package com.nevesrafael.tenki.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.nevesrafael.tenki.data.model.WeatherDetails

@Dao
interface WeatherDao {

    @Query("SELECT * FROM WeatherData")
    fun searchAll(): List<WeatherDetails>

    @Query("SELECT * FROM WeatherData WHERE id = :id")
    fun searchById(id: Long): WeatherDetails?

    @Query("DELETE FROM WeatherData WHERE id = :id")
    fun remove(id: Long)

    @Update
    fun alter(weather: WeatherDetails)

    @Insert
    fun save(weather: WeatherDetails)
}