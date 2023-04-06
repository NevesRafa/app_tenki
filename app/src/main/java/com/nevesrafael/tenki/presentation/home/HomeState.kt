package com.nevesrafael.tenki.presentation.home

import com.nevesrafael.tenki.data.model.CityDetails
import com.nevesrafael.tenki.data.model.WeatherDetails

sealed class HomeState {

    object Loading : HomeState()

    data class Success(val city: CityDetails, val weatherDetails: WeatherDetails) : HomeState()

    object IsFavorite : HomeState()

    object NotFavorite : HomeState()

    data class Error(val errorMessage: String?) : HomeState()
}