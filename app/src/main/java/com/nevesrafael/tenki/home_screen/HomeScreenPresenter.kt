package com.nevesrafael.tenki.home_screen

import androidx.lifecycle.lifecycleScope
import com.nevesrafael.tenki.R
import com.nevesrafael.tenki.model.WeatherApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class HomeScreenPresenter(val screen: HomeScreenActivity) {

    val weatherApi = Retrofit.Builder() //cria o "Criador"
        .baseUrl("https://api.openweathermap.org/") // adiciona URL base
        .addConverterFactory(GsonConverterFactory.create()) // adiciona o cara que converte JSON pra objetos
        .build()
        .create(WeatherApi::class.java) // cria uma instancia da WeatherApi

    fun loadTemperatureData() {
        screen.lifecycleScope.launch {
            screen.showLoading()

            val weatherToday = withContext(Dispatchers.IO) {
                return@withContext weatherApi.getWeatherToday()
            }

            val weatherForecast = withContext(Dispatchers.IO) {
                val answerFromApi = weatherApi.getWeatherForecast()

                val filteredResults = answerFromApi.list
                    .filter { it.date.endsWith("12:00:00") } // filtrando pelo clima de 12:00
                    .takeLast(4) // pegando os ultimos 4 (ignora o hoje)

                return@withContext filteredResults
            }

            screen.hideLoading()

            screen.showOnScreen(weatherToday, weatherForecast)
            changeBackgroundImage(weatherToday.weather.firstOrNull()?.main)
        }
    }

    fun changeBackgroundImage(estado: String?) {
        if (estado == null) {
            return
        }

        val background = when (estado) {
            "Thunderstorm" -> R.drawable.thunderstrorm
            "Drizzle", "Rain" -> R.drawable.drizzle_rain
            "Snow" -> R.drawable.snow
            "Atmosphere" -> R.drawable.atmosphere
            "Clear" -> R.drawable.clear
            "Clouds" -> R.drawable.clouds
            else -> null
        }

        if (background != null) {
            screen.changeBackground(background)
        }
    }

    fun saveCity() {
        //logica de salvar ou não a cidade

        // vc vai salvar ou tirar do banco a cidade

        if (Random.nextBoolean()) {
            // aí se tiver salvo
            screen.showStar()
        } else {
            // e se tiver removido
            screen.hideStar()
        }
    }
}