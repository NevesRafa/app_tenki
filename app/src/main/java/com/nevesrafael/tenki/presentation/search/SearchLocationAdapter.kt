package com.nevesrafael.tenki.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.nevesrafael.tenki.data.remote.CityApiResponse
import com.nevesrafael.tenki.databinding.ItemSearchLocationBinding

class SearchLocationAdapter(private val clickOnTheCity: (CityApiResponse) -> Unit) :
    RecyclerView.Adapter<SearchLocationViewHolder>() {

    private val citySearch = mutableListOf<CityApiResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchLocationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchLocationBinding.inflate(inflater, parent, false)
        return SearchLocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchLocationViewHolder, position: Int) {
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

class SearchLocationViewHolder(val binding: ItemSearchLocationBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        city: CityApiResponse, clickOnTheCity: (CityApiResponse) -> Unit
    ) {

        binding.cityName.text = city.name
        binding.state.text = city.state
        binding.flag.load("https://countryflagsapi.com/png/${city.country}")

        binding.root.setOnClickListener {
            clickOnTheCity(city)
        }
    }
}


