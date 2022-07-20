package com.nevesrafael.tenki.search_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.nevesrafael.tenki.databinding.ItemSavedLocationBinding
import com.nevesrafael.tenki.model.CityApiResponse

class SearchScreenAdapter(private val clickOnTheCity: (CityApiResponse) -> Unit) :
    RecyclerView.Adapter<SearchScreenViewHolder>() {

    val citySearch = mutableListOf<CityApiResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchScreenViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSavedLocationBinding.inflate(inflater, parent, false)
        return SearchScreenViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchScreenViewHolder, position: Int) {
        val item = citySearch[position]
        holder.bind(item, clickOnTheCity)
    }

    override fun getItemCount() = citySearch.size

    fun update(city: List<CityApiResponse>) {
        this.citySearch.clear()
        this.citySearch.addAll(city)
        notifyDataSetChanged()
    }
}

class SearchScreenViewHolder(val binding: ItemSavedLocationBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        city: CityApiResponse, clickOnTheCity: (CityApiResponse) -> Unit
    ) {

        binding.cityName.text = city.name
        binding.state.text = city.state
        binding.country.load("https://countryflagsapi.com/png/${city.country}")

        binding.root.setOnClickListener {
            clickOnTheCity(city)
        }
    }
}


