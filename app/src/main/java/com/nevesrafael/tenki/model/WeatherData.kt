package com.nevesrafael.tenki.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherData(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var state: String,
    var cityName: String,
    var lat: Double,
    var lon: Double,
    var country: String
)
