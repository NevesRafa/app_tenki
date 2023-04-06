//package com.nevesrafael.tenki.presentation.home
//
//import android.content.Context
//import androidx.lifecycle.lifecycleScope
//import com.nevesrafael.tenki.R
//import com.nevesrafael.tenki.data.local.AppDatabase
//import com.nevesrafael.tenki.data.model.CityDetails
//import com.nevesrafael.tenki.data.remote.WeatherApi
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//class HomeScreenPresenter(val screen: HomeActivity) {
//
//    private val weatherDao = AppDatabase.request(screen).weatherDao()
//    private var selectedCity = CityDetails(0, "", "", "")
//
//    companion object {
//        const val SHARED_PREFERENCES_NAME = "db.tenki"
//        const val KEY_COUNTRY = "country"
//        const val KEY_STATE = "state"
//        const val KEY_NAME = "name"
//    }
//
//    fun loadTemperatureData(name: String, country: String, state: String) {
//        selectedCity.cityName = name
//        selectedCity.country = country
//        selectedCity.state = state
//
//        if (name == "") {
//            //recupera a ultima cidade pesquisada no sharedPreference
//            val sharedPreference = screen.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
//
//            selectedCity.country = sharedPreference.getString(KEY_COUNTRY, null) ?: "BR"
//            selectedCity.state = sharedPreference.getString(KEY_STATE, null) ?: "São Paulo"
//            selectedCity.cityName = sharedPreference.getString(KEY_NAME, null) ?: "Sorocaba"
//        }
//
//        screen.lifecycleScope.launch {
//            screen.showLoading()
//
//            val weatherToday = withContext(Dispatchers.IO) {
//                return@withContext weatherApi.getWeatherToday(selectedCity.cityName)
//            }
//
//            val weatherForecast = withContext(Dispatchers.IO) {
//                val answerFromApi = weatherApi.getWeatherForecast(selectedCity.cityName)
//
//                val filteredResults = answerFromApi.list
//                    .filter { it.date.endsWith("15:00:00") } // filtrando pelo clima de 15:00
//                    .takeLast(4) // pegando os ultimos 4 (ignora o hoje)
//
//                return@withContext filteredResults
//            }
//
//            screen.hideLoading()
//
//            screen.showOnScreen(weatherToday, weatherForecast)
//            changeBackgroundImage(weatherToday.weather.firstOrNull()?.main, weatherToday.weather.first().icon)
//            fillSelectedCity(weatherToday.name, weatherToday.id)
//            saveCityInMemory()
//            checkFavorite()
//        }
//    }
//
//    fun fillSelectedCity(cityName: String, cityId: Long) {
//        selectedCity.cityName = cityName
//        selectedCity.id = cityId
//    }
//
//
//
//    //para salvar a ultima cidade pesquisada
//    private fun saveCityInMemory() {
//        val sharedPreference = screen.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
//        val editor = sharedPreference.edit()
//        editor.putString(KEY_NAME, selectedCity.cityName)
//        editor.putString(KEY_COUNTRY, selectedCity.country)
//        editor.putString(KEY_STATE, selectedCity.state)
//        editor.apply()
//    }
//}
