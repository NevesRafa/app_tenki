package com.nevesrafael.tenki.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val state: String,
    val flag: String,
    val cityName: String,
    val lat: Long,
    val lon: Long,
    val saveCity: Boolean,
    val country: String
)
