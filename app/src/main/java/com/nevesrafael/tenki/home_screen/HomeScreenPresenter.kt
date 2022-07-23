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
        const val KEY_LAT = "latitude"
        const val KEY_LON = "longitude"
    }

    val weatherApi = Retrofit.Builder() //cria o "Criador"
        .baseUrl("https://api.openweathermap.org/") // adiciona URL base
        .addConverterFactory(GsonConverterFactory.create()) // adiciona o cara que converte JSON pra objetos
        .build()
        .create(WeatherApi::class.java) // cria uma instancia da WeatherApi

    fun loadTemperatureData(lat: Double, lon: Double, country: String, state: String) {
        selectedCity.lat = lat
        selectedCity.lon = lon
        selectedCity.country = country
        selectedCity.state = state

        if (lat == 0.0 && lon == 0.0) {
            //recupera a ultima cidade pesquisada no sharedPreference
            val sharedPreference = screen.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

            selectedCity.lat = sharedPreference.getString(KEY_LAT, null)?.toDoubleOrNull() ?: -23.5003451
            selectedCity.lon = sharedPreference.getString(KEY_LON, null)?.toDoubleOrNull() ?: -47.4582864
            selectedCity.country = sharedPreference.getString("KEY_COUNTRY", null) ?: "BR"
            selectedCity.state = sharedPreference.getString("KEY_STATE", null) ?: "São Paulo"

        }

        screen.lifecycleScope.launch {
            screen.showLoading()

            val weatherToday = withContext(Dispatchers.IO) {
                return@withContext weatherApi.getWeatherToday(selectedCity.lat, selectedCity.lon)
            }

            val weatherForecast = withContext(Dispatchers.IO) {
                val answerFromApi = weatherApi.getWeatherForecast(selectedCity.lat, selectedCity.lon)

                val filteredResults = answerFromApi.list
                    .filter { it.date.endsWith("15:00:00") } // filtrando pelo clima de 15:00
                    .takeLast(4) // pegando os ultimos 4 (ignora o hoje)

                return@withContext filteredResults
            }

            screen.hideLoading()



            screen.showOnScreen(weatherToday, weatherForecast)
            changeBackgroundImage(weatherToday.weather.firstOrNull()?.main)
            fillSelectedCity(weatherToday.name, weatherToday.id)
            saveCityInMemory()
            checkFavorite()
        }
    }

    fun fillSelectedCity(cityName: String, cityId: Long) {
        selectedCity.cityName = cityName
        selectedCity.id = cityId

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
        editor.putString(KEY_LAT, selectedCity.lat.toString())
        editor.putString(KEY_LON, selectedCity.lon.toString())
        editor.putString("KEY_COUNTRY", selectedCity.country)
        editor.putString("KEY_STATE", selectedCity.state)
        editor.apply()
    }
}
