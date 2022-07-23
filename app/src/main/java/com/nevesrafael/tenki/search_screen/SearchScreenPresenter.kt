package com.nevesrafael.tenki.search_screen

import androidx.lifecycle.lifecycleScope
import com.nevesrafael.tenki.database.AppDatabase
import com.nevesrafael.tenki.model.CityApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchScreenPresenter(val screen: SearchScreenActivity) {

    private val weatherDao = AppDatabase.request(screen).weatherDao()

    val cityApi = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CityApi::class.java)

    fun loadSearchCity(search: String) {

        if (search.isBlank()) {
            screen.searchIsBlank()
        } else {

            screen.lifecycleScope.launch {

                screen.showLoading()

                val searchCity = withContext(Dispatchers.IO) {
                    return@withContext cityApi.getCity(search)
                }

                screen.hideLoading()

                if (searchCity.isNotEmpty()) {
                    screen.showListCity(searchCity)
                } else {
                    screen.searchError()
                }
            }
        }
    }

    fun savedCities() {
        val listOfCities = weatherDao.searchAll()
        screen.showListSavedCities(listOfCities)
    }
}