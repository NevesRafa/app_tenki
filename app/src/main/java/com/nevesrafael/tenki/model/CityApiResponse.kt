package com.nevesrafael.tenki.model

data class CityApiResponse(

    val name: String,
    val lat: Long,
    val lon: Long,
    val country: String,
    val state: String
)