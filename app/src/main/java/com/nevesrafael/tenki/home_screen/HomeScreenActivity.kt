package com.nevesrafael.tenki.home_screen

import android.app.Activity
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

    companion object {
        const val EXTRA_CITY_NAME = "extra.city.name"
        const val REQUEST_CODE_CITY_NAME = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = HomeScreenPresenter(this)

        configureSearchButton()
        configureRecyclerViewWeather()
        configureStarButton()
        presenter.loadTemperatureData(null)
    }

    private fun configureStarButton() {
        binding.star.setOnClickListener {
            presenter.favoriteCity()
        }
    }

    private fun configureRecyclerViewWeather() {
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
        binding.image.visibility = View.GONE
        binding.star.visibility = View.GONE
        binding.search.visibility = View.GONE
        binding.recyclerviewWeatherForecast.visibility = View.GONE
        binding.shapeWeatherForecast.visibility = View.GONE
        binding.cityName.visibility = View.GONE
        binding.climate.visibility = View.GONE
        binding.temperature.visibility = View.GONE
    }

    fun hideLoading() {
        binding.loading.visibility = View.GONE
        binding.image.visibility = View.VISIBLE
        binding.star.visibility = View.VISIBLE
        binding.search.visibility = View.VISIBLE
        binding.recyclerviewWeatherForecast.visibility = View.VISIBLE
        binding.shapeWeatherForecast.visibility = View.VISIBLE
        binding.cityName.visibility = View.VISIBLE
        binding.climate.visibility = View.VISIBLE
        binding.temperature.visibility = View.VISIBLE
    }

    private fun configureSearchButton() {

        binding.search.setOnClickListener {
            val intent = Intent(this, SearchScreenActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_CITY_NAME)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_CITY_NAME && resultCode == Activity.RESULT_OK) {
            val cityName = data?.getStringExtra(EXTRA_CITY_NAME)

            presenter.saveCity(cityName)

            presenter.loadTemperatureData(cityName)
        }
    }


}