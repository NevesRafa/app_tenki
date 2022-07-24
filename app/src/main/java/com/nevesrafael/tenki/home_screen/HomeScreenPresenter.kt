package com.nevesrafael.tenki.home_screen

import android.content.Context
import androidx.lifecycle.lifecycleScope
import com.nevesrafael.tenki.R
import com.nevesrafael.tenki.database.AppDatabase
import com.nevesrafael.tenki.model.WeatherApi
import com.nevesrafael.tenki.model.WeatherData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeScreenPresenter(val screen: HomeScreenActivity) {

    private val weatherDao = AppDatabase.request(screen).weatherDao()
    private var selectedCity = WeatherData(0, "", "", 0.0, 0.0, "")

    companion object {
        const val SHARED_PREFERENCES_NAME = "db.tenki"
        const val KEY_COUNTRY = "country"
        const val KEY_STATE = "state"
        const val KEY_NAME = "name"
    }

    val weatherApi = Retrofit.Builder() //cria o "Criador"
        .baseUrl("https://api.openweathermap.org/") // adiciona URL base
        .addConverterFactory(GsonConverterFactory.create()) // adiciona o cara que converte JSON pra objetos
        .build()
        .create(WeatherApi::class.java) // cria uma instancia da WeatherApi

    fun loadTemperatureData(name: String, country: String, state: String) {
        selectedCity.cityName = name
        selectedCity.country = country
        selectedCity.state = state

        if (name == "") {
            //recupera a ultima cidade pesquisada no sharedPreference
            val sharedPreference = screen.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

            selectedCity.country = sharedPreference.getString(KEY_COUNTRY, null) ?: "BR"
            selectedCity.state = sharedPreference.getString(KEY_STATE, null) ?: "São Paulo"
            selectedCity.cityName = sharedPreference.getString(KEY_NAME, null) ?: "Sorocaba"


        }

        screen.lifecycleScope.launch {
            screen.showLoading()

            val weatherToday = withContext(Dispatchers.IO) {
                return@withContext weatherApi.getWeatherToday(selectedCity.cityName)
            }

            val weatherForecast = withContext(Dispatchers.IO) {
                val answerFromApi = weatherApi.getWeatherForecast(selectedCity.cityName)

                val filteredResults = answerFromApi.list
                    .filter { it.date.endsWith("15:00:00") } // filtrando pelo clima de 15:00
                    .takeLast(4) // pegando os ultimos 4 (ignora o hoje)

                return@withContext filteredResults
            }

            screen.hideLoading()



            screen.showOnScreen(weatherToday, weatherForecast)
            changeBackgroundImage(weatherToday.weather.firstOrNull()?.main, weatherToday.weather.first().icon)
            fillSelectedCity(weatherToday.name, weatherToday.id)
            saveCityInMemory()
            checkFavorite()
        }
    }

    fun fillSelectedCity(cityName: String, cityId: Long) {
        selectedCity.cityName = cityName
        selectedCity.id = cityId

    }

    fun changeBackgroundImage(state: String?, weather: String) {
        if (state == null) {
            return
        }

        val background = when {
            state == "Thunderstorm" && weather == "11d" -> {
                R.drawable.thunderstrorm_day
            }
            state == "Thunderstorm" && weather == "11n" -> {
                R.drawable.thunderstrorm_night
            }
            state == "Drizzle" || state == "Rain" && weather == "09d" || weather == "10d" -> {
                R.drawable.drizzle_rain_day
            }
            state == "Drizzle" || state == "Rain" && weather == "09n" || weather == "10n" -> {
                R.drawable.drizzle_rain_night
            }
            state == "Snow" && weather == "13d" -> {
                R.drawable.snow_day
            }
            state == "Snow" && weather == "13n" -> {
                R.drawable.snow_night
            }
            state == "Atmosphere" && weather == "50d" || weather == "50n" -> {
                R.drawable.atmosphere
            }
            state == "Clear" && weather == "01d" -> {
                R.drawable.clear_day
            }
            state == "Clear" && weather == "01n" -> {
                R.drawable.clear_night
            }
            state == "Clouds" && weather == "02d" || weather == "03d" || weather == "04d" -> {
                R.drawable.clouds_day
            }
            state == "Clouds" && weather == "02n" || weather == "03n" || weather == "04n" -> {
                R.drawable.clouds_night
            }
            else -> {
                null
            }
        }

        if (background != null) {
            screen.changeBackground(background)
        }
    }

    fun checkFavorite() {
        val city = weatherDao.searchById(selectedCity.id)

        if (city != null) {
            screen.showStar()

        } else {
            screen.hideStar()
        }
    }

    fun favoriteCity() {
        val city = weatherDao.searchById(selectedCity.id)

        if (city != null) {
            weatherDao.remove(selectedCity.id)
        } else {
            weatherDao.save(selectedCity)
        }

        checkFavorite()
    }


    //para salvar a ultima cidade pesquisada
    fun saveCityInMemory() {
        val sharedPreference = screen.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString(KEY_NAME, selectedCity.cityName)
        editor.putString(KEY_COUNTRY, selectedCity.country)
        editor.putString(KEY_STATE, selectedCity.state)
        editor.apply()
    }
}
