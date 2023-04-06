package com.nevesrafael.tenki.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface CityApi {

    @GET("geo/1.0/direct")
    suspend fun getCity(
        @Query("q") city: String,
        @Query("limit") limit: String = "5",
        @Query("appid") key: String = "a164dd5e11d56abc5b28c37155fc0e4e"
    ): List<CityApiResponse>
}

