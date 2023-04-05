package com.nevesrafael.tenki.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nevesrafael.tenki.data.model.WeatherDetails
import com.nevesrafael.tenki.domain.TenkiRepository
import kotlinx.coroutines.launch

class HomeViewModel(val repository: TenkiRepository) : ViewModel() {

    val tenkiLiveData = MutableLiveData<HomeState>()

    fun starCondition(id: Long) {
        viewModelScope.launch {
            if (repository.cityById(id) != null) {
                tenkiLiveData.postValue(HomeState.IsFavorite)
            } else {
                tenkiLiveData.postValue(HomeState.NotFavorite)
            }
        }
    }

    fun clickStar(city: WeatherDetails) {
        viewModelScope.launch {
            if (repository.cityById(city.id) != null) {
                repository.removeCityById(city.id)
            } else {
                repository.saveCity(city)
            }
            starCondition(city.id)
        }
    }
}