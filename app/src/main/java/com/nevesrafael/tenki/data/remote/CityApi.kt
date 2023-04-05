package com.nevesrafael.tenki.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface CityApi {

    @GET("geo/1.0/direct")
    suspend fun getCity(
        @Query("q") city: String,
        @Query("limit") limit: String = "5",
        @Query("appid") key: String = "87c2ecbeb52654589432513009c0bd19"
    ): List<CityApiResponse>
}

