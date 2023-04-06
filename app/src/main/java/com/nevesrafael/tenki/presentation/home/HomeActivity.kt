package com.nevesrafael.tenki.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.nevesrafael.tenki.R
import com.nevesrafael.tenki.data.model.CityDetails
import com.nevesrafael.tenki.data.model.WeatherDetails
import com.nevesrafael.tenki.databinding.ActivityHomeScreenBinding
import com.nevesrafael.tenki.internal.Ext.gone
import com.nevesrafael.tenki.internal.Ext.setErrorStyle
import com.nevesrafael.tenki.internal.Ext.visible
import com.nevesrafael.tenki.presentation.search.SearchActivity
import org.koin.android.ext.android.inject

class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by inject()

    private lateinit var binding: ActivityHomeScreenBinding
    private lateinit var weatherAdapter: HomeScreenAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()

        setupViewModel()
        setupRecyclerView()
        configureSearchButton()
        configureStarButton()

    }

    private fun setupViewModel() {
        viewModel.tenkiLiveData.observe(this) { state ->
            when (state) {
                is HomeState.Loading -> showLoading()
                is HomeState.Success -> showOnScreen(state.city, state.weatherDetails)
                is HomeState.IsFavorite -> showStar()
                is HomeState.NotFavorite -> hideStar()
                is HomeState.Error -> showError(state.errorMessage)
            }
        }
    }

    private fun setupRecyclerView() {
        hideLoading()
        weatherAdapter = HomeScreenAdapter()
        binding.recyclerviewWeatherForecast.adapter = weatherAdapter
    }

    fun showOnScreen(
        city: CityDetails,
        weatherDetails: WeatherDetails
    ) {

        binding.cityName.text = city.cityName


        binding.temperature.text = getString(R.string.temperature, weatherDetails.temp)

        binding.climate.text = weatherDetails.description

        weatherAdapter.update(weatherDetails.weatherForecast)
    }

    private fun showError(errorMessage: String?) {
        hideLoading()
        Snackbar.make(
            binding.root,
            getString(R.string.error_message, errorMessage),
            Snackbar.LENGTH_LONG
        )
            .setErrorStyle()
            .show()
    }

    fun showLoading() {
        binding.loading.visible()
        binding.image.gone()
        binding.star.gone()
        binding.search.gone()
        binding.recyclerviewWeatherForecast.gone()
        binding.shapeWeatherForecast.gone()
        binding.cityName.gone()
        binding.climate.gone()
        binding.temperature.gone()
    }

    fun hideLoading() {
        binding.loading.gone()
        binding.image.visible()
        binding.star.visible()
        binding.search.visible()
        binding.recyclerviewWeatherForecast.visible()
        binding.shapeWeatherForecast.visible()
        binding.cityName.visible()
        binding.climate.visible()
        binding.temperature.visible()
    }

    private fun configureSearchButton() {

        binding.search.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_SEARCH)
        }
    }

    private fun configureStarButton() {
        binding.star.setOnClickListener {
            viewModel.clickStar()
        }
    }

    private fun showStar() {
        val starSelected = ContextCompat.getDrawable(this, R.drawable.ic_star_selected)

        binding.star.setImageDrawable(starSelected)
    }

    private fun hideStar() {
        val starUnselected = ContextCompat.getDrawable(this, R.drawable.ic_star_unselected)

        binding.star.setImageDrawable(starUnselected)
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == REQUEST_CODE_SEARCH && resultCode == Activity.RESULT_OK) {
//            val country = data?.getStringExtra(EXTRA_CITY_COUNTRY) ?: ""
//            val state = data?.getStringExtra(EXTRA_CITY_STATE) ?: ""
//            val name = data?.getStringExtra(EXTRA_CITY_NAME) ?: ""
//
//            presenter.loadTemperatureData(name, country, state)
//
//        }
//
//    }

    companion object {
        const val EXTRA_CITY_COUNTRY = "extra.city.country"
        const val EXTRA_CITY_STATE = "extra.city.state"
        const val EXTRA_CITY_NAME = "extra.city.name"
        const val REQUEST_CODE_SEARCH = 123
    }
}