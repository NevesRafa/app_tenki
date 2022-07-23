package com.nevesrafael.tenki.search_screen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.nevesrafael.tenki.databinding.ActivitySearchScreenBinding
import com.nevesrafael.tenki.home_screen.HomeScreenActivity
import com.nevesrafael.tenki.model.CityApiResponse
import com.nevesrafael.tenki.model.WeatherData

class SearchScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchScreenBinding
    private lateinit var presenter: SearchScreenPresenter
    private lateinit var searchAdapter: SearchLocationAdapter
    private lateinit var saveAdapter: SavedLocationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = SearchScreenPresenter(this)

        clickToSearch()
        configureRecyclerViewSearchLocation()
        configureRecyclerViewSavedLocation()
        presenter.savedCities()
    }


    fun clickToSearch() {
        binding.searchInputText.setEndIconOnClickListener {
            val search = binding.search.text.toString()
            presenter.loadSearchCity(search)
        }
    }

    fun showLoading() {
        binding.loading.visibility = View.VISIBLE

    }

    fun hideLoading() {
        binding.loading.visibility = View.GONE

    }

    fun searchError() {
        Toast.makeText(this, "Not found!", Toast.LENGTH_SHORT).show()
    }

    private fun configureRecyclerViewSavedLocation() {
        saveAdapter = SavedLocationAdapter(savedCityClick = { city ->
            val intentToReturn = Intent()
            intentToReturn.putExtra(HomeScreenActivity.EXTRA_CITY_LAT, city.lat)
            intentToReturn.putExtra(HomeScreenActivity.EXTRA_CITY_LON, city.lon)
            setResult(Activity.RESULT_OK, intentToReturn)
            finish()
        })
        binding.recyclerviewSaveLocation.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewSaveLocation.adapter = saveAdapter
        binding.recyclerviewSaveLocation.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayout.VERTICAL
            )
        )
    }

    fun configureRecyclerViewSearchLocation() {
        searchAdapter = SearchLocationAdapter(clickOnTheCity = { city ->
            val intentToReturn = Intent()
            intentToReturn.putExtra(HomeScreenActivity.EXTRA_CITY_LAT, city.lat)
            intentToReturn.putExtra(HomeScreenActivity.EXTRA_CITY_LON, city.lon)
            intentToReturn.putExtra(HomeScreenActivity.EXTRA_CITY_COUNTRY, city.country)
            intentToReturn.putExtra(HomeScreenActivity.EXTRA_CITY_STATE, city.state)
            setResult(Activity.RESULT_OK, intentToReturn)
            finish()
        })
        binding.recyclerviewSearchResult.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewSearchResult.adapter = searchAdapter
        binding.recyclerviewSearchResult.addItemDecoration(
            DividerItemDecoration(this, LinearLayout.VERTICAL)
        )
    }

    fun showListCity(searchCity: List<CityApiResponse>) {
        searchAdapter.update(searchCity)
    }

    fun searchIsBlank() {
        Toast.makeText(this, "Please fill in the search field!", Toast.LENGTH_SHORT).show()
    }

    fun showListSavedCities(listOfCities: List<WeatherData>) {
        saveAdapter.update(listOfCities)
    }
}