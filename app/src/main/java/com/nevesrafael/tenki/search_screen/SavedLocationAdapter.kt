package com.nevesrafael.tenki.search_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.nevesrafael.tenki.databinding.ItemSavedLocationBinding
import com.nevesrafael.tenki.model.WeatherData

class SavedLocationAdapter(private val savedCityClick: (WeatherData) -> Unit) :
    RecyclerView.Adapter<SavedLocationViewHolder>() {

    val citySaves = mutableListOf<WeatherData>()

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

    fun update(city: List<WeatherData>) {
        this.citySaves.clear()
        this.citySaves.addAll(city)
        notifyDataSetChanged()
    }
}

class SavedLocationViewHolder(val binding: ItemSavedLocationBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(city: WeatherData, savedCityClick: (WeatherData) -> Unit) {

        binding.cityName.text = city.cityName
        binding.state.text = city.state
        binding.flag.load("https://countryflagsapi.com/png/${city.country}")

        binding.root.setOnClickListener {
            savedCityClick(city)
        }
    }

}
