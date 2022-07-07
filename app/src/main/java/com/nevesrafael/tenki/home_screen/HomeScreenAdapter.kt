package com.nevesrafael.tenki.home_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nevesrafael.tenki.databinding.ItemWeekdayInformationBinding
import com.nevesrafael.tenki.model.WeatherData

class HomeScreenAdapter() : RecyclerView.Adapter<HomeScreenViewHolder>() {

    val dataset = mutableListOf<WeatherData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeScreenViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWeekdayInformationBinding.inflate(inflater, parent, false)
        return HomeScreenViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeScreenViewHolder, position: Int) {
        val item = dataset[position]
        holder.bind(item)
    }

    override fun getItemCount() = dataset.size

    fun update(dataset: List<WeatherData>) {
        this.dataset.clear()
        this.dataset.addAll(dataset)
        notifyDataSetChanged()
    }
}


class HomeScreenViewHolder(val binding: ItemWeekdayInformationBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(dataset: WeatherData) {
        binding.weekdayForecast.text = dataset.weekday
        binding.temperatureForecast.text = dataset.temperature.toString()
        binding.stateForecast.text = dataset.state
    }

}
