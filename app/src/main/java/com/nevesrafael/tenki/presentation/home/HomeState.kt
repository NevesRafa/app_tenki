package com.nevesrafael.tenki.presentation.home

sealed class HomeState {

    object Loading : HomeState()

    object Success : HomeState()

    object SuccessCitySearch : HomeState()

    object IsFavorite : HomeState()

    object NotFavorite : HomeState()

    object Error : HomeState()
}