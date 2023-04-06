package com.nevesrafael.tenki.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CityDetails(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var state: String,
    var cityName: String,
    var country: String
)
