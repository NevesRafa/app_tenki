package com.nevesrafael.tenki.home_screen

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.nevesrafael.tenki.TemperatureFormatter
import com.nevesrafael.tenki.databinding.ActivityHomeScreenBinding
import com.nevesrafael.tenki.model.WeatherDataApiResponse
import com.nevesrafael.tenki.model.WeatherTodayApiResponse
import com.nevesrafael.tenki.search_screen.SearchScreenActivity

class HomeScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeScreenBinding
    private lateinit var presenter: HomeScreenPresenter
    private lateinit var weatherAdapter: HomeScreenAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = HomeScreenPresenter(this)

        configureSearchButton()
        configuraRecyclerViewWeather()
        presenter.loadTemperatureData()
    }


    private fun configuraRecyclerViewWeather() {
        weatherAdapter = HomeScreenAdapter()
        binding.recyclerviewWeatherForecast.adapter = weatherAdapter
    }


    fun showOnScreen(
        weatherToday: WeatherTodayApiResponse,
        weatherForecast: List<WeatherDataApiResponse>
    ) {
        binding.cityName.text = weatherToday.name
        binding.temperature.text =
            TemperatureFormatter.kelvinToCelsius(weatherToday.main.temp).toString()
        binding.climate.text = weatherToday.weather[0].description

        weatherAdapter.update(weatherForecast)
    }

    fun showLoading() {
        binding.loading.visibility = View.VISIBLE
    }

    fun hideLoading() {
        binding.loading.visibility = View.GONE
    }

    private fun configureSearchButton() {

        binding.search.setOnClickListener {
            val intent = Intent(this, SearchScreenActivity::class.java)
            startActivity(intent)
        }

    }
}