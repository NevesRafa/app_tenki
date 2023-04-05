package com.nevesrafael.tenki.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.nevesrafael.tenki.data.model.WeatherDetails
import com.nevesrafael.tenki.databinding.ItemSavedLocationBinding

class SavedLocationAdapter(private val savedCityClick: (WeatherDetails) -> Unit) :
    RecyclerView.Adapter<SavedLocationViewHolder>() {

    private val citySaves = mutableListOf<WeatherDetails>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedLocationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSavedLocationBinding.inflate(inflater, parent, false)
        return SavedLocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedLocationViewHolder, position: Int) {
        val item = citySaves[position]
        holder.bind(item, savedCityClick)
    }

    override fun getItemCount() = citySaves.size

    fun update(city: List<WeatherDetails>) {
        this.citySaves.clear()
        this.citySaves.addAll(city)
        notifyDataSetChanged()
    }
}

class SavedLocationViewHolder(val binding: ItemSavedLocationBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(city: WeatherDetails, savedCityClick: (WeatherDetails) -> Unit) {

        binding.cityName.text = city.cityName
        binding.state.text = city.state
        binding.flag.load("https://countryflagsapi.com/png/${city.country}")

        binding.root.setOnClickListener {
            savedCityClick(city)
        }
    }

}
