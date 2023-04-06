package com.nevesrafael.tenki.presentation.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nevesrafael.tenki.data.local.AppDatabase
import com.nevesrafael.tenki.data.model.CityDetails
import com.nevesrafael.tenki.domain.TenkiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val weatherDao = AppDatabase.request(application.applicationContext).weatherDao()
    private val repository = TenkiRepository(weatherDao)
    val tenkiLiveData = MutableLiveData<HomeState>()
    var city: CityDetails = CityDetails(
        id = 0,
        state = "São Paulo",
        cityName = "Sorocaba",
        country = "BR"
    )

    private fun starCondition(id: Long) {
        viewModelScope.launch {
            if (repository.cityById(id) != null) {
                tenkiLiveData.postValue(HomeState.IsFavorite)
            } else {
                tenkiLiveData.postValue(HomeState.NotFavorite)
            }
        }
    }

    fun clickStar() {
        viewModelScope.launch {
            if (repository.cityById(city.id) != null) {
                repository.removeCityById(city.id)
            } else {
                repository.saveCity(city)
            }
            starCondition(city.id)
        }
    }

    fun weatherToday() {
        viewModelScope.launch {

            tenkiLiveData.postValue(HomeState.Loading)

            try {
                val weatherDetails = withContext(Dispatchers.IO) {
                    repository.callWeatherToday(city.cityName)
                }
                tenkiLiveData.postValue(HomeState.Success(city, weatherDetails))

            } catch (error: Exception) {
                tenkiLiveData.postValue(HomeState.Error(error.message))
            }
        }
    }
}