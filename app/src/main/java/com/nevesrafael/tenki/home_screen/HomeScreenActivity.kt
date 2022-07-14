package com.nevesrafael.tenki.home_screen

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.nevesrafael.tenki.R
import com.nevesrafael.tenki.TemperatureFormatter
import com.nevesrafael.tenki.databinding.ActivityHomeScreenBinding
import com.nevesrafael.tenki.model.WeatherDataApiResponse
import com.nevesrafael.tenki.model.WeatherTodayApiResponse
import com.nevesrafael.tenki.search_screen.SearchScreenActivity
import java.util.*

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
        configureStarButton()
        presenter.loadTemperatureData()
    }

    private fun configureStarButton() {
        binding.star.setOnClickListener {
            presenter.saveCity()
        }
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

        val temp = TemperatureFormatter.kelvinToCelsius(weatherToday.main.temp)
        binding.temperature.text = getString(R.string.temperature, temp)

        binding.climate.text = weatherToday.weather.first().description.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        }
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

    fun changeBackground(background: Int) {
        val backgroundDrawable = ContextCompat.getDrawable(this, background)
        binding.image.setImageDrawable(backgroundDrawable)
    }

    fun showStar() {
        val starSelected = ContextCompat.getDrawable(this, R.drawable.ic_star_selected)

        binding.star.setImageDrawable(starSelected)
    }

    fun hideStar() {
        val starUnselected = ContextCompat.getDrawable(this, R.drawable.ic_star_unselected)

        binding.star.setImageDrawable(starUnselected)
    }
}