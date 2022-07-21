package com.nevesrafael.tenki.model

data class CityApiResponse(

    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String
)