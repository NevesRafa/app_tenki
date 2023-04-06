package com.nevesrafael.tenki.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.nevesrafael.tenki.data.model.CityDetails

@Dao
interface WeatherDao {

    @Query("SELECT * FROM CityDetails")
    fun searchAll(): List<CityDetails>

    @Query("SELECT * FROM CityDetails WHERE id = :id")
    fun searchById(id: Long): CityDetails?

    @Query("DELETE FROM CityDetails WHERE id = :id")
    fun remove(id: Long)

    @Update
    fun alter(weather: CityDetails)

    @Insert
    fun save(weather: CityDetails)
}