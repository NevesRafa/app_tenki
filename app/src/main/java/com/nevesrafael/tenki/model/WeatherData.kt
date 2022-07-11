package com.nevesrafael.tenki.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val temperature: Int,
    val weekday: String,
    val state: String,
    val localeName: String
)
