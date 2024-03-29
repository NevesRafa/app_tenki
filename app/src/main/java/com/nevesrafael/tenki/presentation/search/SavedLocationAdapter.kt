package com.nevesrafael.tenki.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.nevesrafael.tenki.data.model.CityDetails
import com.nevesrafael.tenki.databinding.ItemSavedLocationBinding

class SavedLocationAdapter(private val savedCityClick: (CityDetails) -> Unit) :
    RecyclerView.Adapter<SavedLocationViewHolder>() {

    private val citySaves = mutableListOf<CityDetails>()

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

    fun update(city: List<CityDetails>) {
        this.citySaves.clear()
        this.citySaves.addAll(city)
        notifyDataSetChanged()
    }
}

class SavedLocationViewHolder(val binding: ItemSavedLocationBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(city: CityDetails, savedCityClick: (CityDetails) -> Unit) {

        binding.cityName.text = city.cityName
        binding.state.text = city.state
        binding.flag.load("https://countryflagsapi.com/png/${city.country}")

        binding.root.setOnClickListener {
            savedCityClick(city)
        }
    }

}
