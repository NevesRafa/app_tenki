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

class SearchScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchScreenBinding
    private lateinit var presenter: SearchScreenPresenter
    private lateinit var searchAdapter: SearchScreenAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = SearchScreenPresenter(this)

        clickToSearch()
        configureRecyclerViewSearchCity()
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

    fun configureRecyclerViewSearchCity() {
        searchAdapter = SearchScreenAdapter(clickOnTheCity = { city ->
            val intentToReturn = Intent()
            intentToReturn.putExtra(HomeScreenActivity.EXTRA_CITY_NAME, city.name)
            setResult(Activity.RESULT_OK, intentToReturn)
            finish()
        })
        binding.recyclerviewSearchResult.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewSearchResult.adapter = searchAdapter
        binding.recyclerviewSearchResult.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayout.VERTICAL
            )
        )
    }

    fun showListCity(searchCity: List<CityApiResponse>) {
        searchAdapter.update(searchCity)
    }

    fun searchIsBlank() {
        Toast.makeText(this, "Please fill in the search field!", Toast.LENGTH_SHORT).show()
    }
}