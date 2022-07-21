package com.nevesrafael.tenki.home_screen

import android.content.Context
import androidx.lifecycle.lifecycleScope
import com.nevesrafael.tenki.R
import com.nevesrafael.tenki.database.AppDatabase
import com.nevesrafael.tenki.model.WeatherApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class HomeScreenPresenter(val screen: HomeScreenActivity) {

    private val WeatherDao = AppDatabase.request(screen).weatherDao()

    companion object {
        const val SHARED_PREFERENCES_NAME = "db.tenki"
        const val KEY_LAT = "latitude"
        const val KEY_LON = "longitude"
    }

    val weatherApi = Retrofit.Builder() //cria o "Criador"
        .baseUrl("https://api.openweathermap.org/") // adiciona URL base
        .addConverterFactory(GsonConverterFactory.create()) // adiciona o cara que converte JSON pra objetos
        .build()
        .create(WeatherApi::class.java) // cria uma instancia da WeatherApi

    fun loadTemperatureData(lat: Long?, long: Long?) {
        var latitude = lat ?: 0L
        var longitude = long ?: 0L

        if (latitude == 0L && longitude == 0L) {
            //recupera a ultima cidade pesquisada no sharedPreference
            val sharedPreference =
                screen.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

            latitude = sharedPreference.getLong(KEY_LAT, (-23.5003451).toLong())
            longitude = sharedPreference.getLong(KEY_LON, (-47.4582864).toLong())
        }

        screen.lifecycleScope.launch {
            screen.showLoading()

            val weatherToday = withContext(Dispatchers.IO) {
                return@withContext weatherApi.getWeatherToday(latitude, longitude)
            }

            val weatherForecast = withContext(Dispatchers.IO) {
                val answerFromApi = weatherApi.getWeatherForecast(latitude, longitude)

                val filteredResults = answerFromApi.list
                    .filter { it.date.endsWith("15:00:00") } // filtrando pelo clima de 15:00
                    .takeLast(4) // pegando os ultimos 4 (ignora o hoje)

                return@withContext filteredResults
            }

            screen.hideLoading()

            screen.showOnScreen(weatherToday, weatherForecast)
            changeBackgroundImage(weatherToday.weather.firstOrNull()?.main)
        }
    }

    fun changeBackgroundImage(state: String?) {
        if (state == null) {
            return
        }

        val background = when (state) {
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

    fun favoriteCity() {
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


    //para salvar a ultima cidade pesquisada
    fun saveCity(lat: Long?, lon: Long?) {
        if (lat == null || lon == null) {
            return
        }

        val sharedPreference =
            screen.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putLong(KEY_LAT, lat)
        editor.putLong(KEY_LON, lon)
        editor.apply()

    }
}
