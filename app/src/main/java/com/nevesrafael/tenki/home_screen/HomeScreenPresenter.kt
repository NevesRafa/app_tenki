package com.nevesrafael.tenki.home_screen

import androidx.lifecycle.lifecycleScope
import com.nevesrafael.tenki.model.WeatherApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeScreenPresenter(val screen: HomeScreenActivity) {

    val weatherApi = Retrofit.Builder() //cria o "Criador"
        .baseUrl("https://api.openweathermap.org/") // adiciona URL base
        .addConverterFactory(GsonConverterFactory.create()) // adiciona o cara que converte JSON pra objetos
        .build()
        .create(WeatherApi::class.java) // cria uma instancia da CafeApi

    fun loadTemperatureData() {
        screen.lifecycleScope.launch {
            screen.showLoading()

            val weatherToday = withContext(Dispatchers.IO) {
                return@withContext weatherApi.getWeatherToday()
            }

            val weatherForecast = withContext(Dispatchers.IO) {
                val answerFromApi = weatherApi.getWeatherForecast()

                val filteredResults = answerFromApi.list
                    .filter { it.date.endsWith("15:00:00") } // filtrando pelo clima de 15:00
                    .takeLast(4) // pegando os ultimos 4 (ignora o hoje)

                return@withContext filteredResults
            }



            screen.hideLoading()

            screen.showOnScreen(weatherToday, weatherForecast)
        }
    }

}