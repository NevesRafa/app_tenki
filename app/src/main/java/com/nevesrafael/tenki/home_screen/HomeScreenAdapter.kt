package com.nevesrafael.tenki.home_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nevesrafael.tenki.TemperatureFormatter
import com.nevesrafael.tenki.databinding.ItemWeekdayInformationBinding
import com.nevesrafael.tenki.model.WeatherDataApiResponse

class HomeScreenAdapter() : RecyclerView.Adapter<HomeScreenViewHolder>() {

    val weatherForecast = mutableListOf<WeatherDataApiResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeScreenViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWeekdayInformationBinding.inflate(inflater, parent, false)
        return HomeScreenViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeScreenViewHolder, position: Int) {
        val item = weatherForecast[position]
        holder.bind(item)
    }

    override fun getItemCount() = weatherForecast.size

    fun update(weather: List<WeatherDataApiResponse>) {
        this.weatherForecast.clear()
        this.weatherForecast.addAll(weather)
        notifyDataSetChanged()
    }
}

class HomeScreenViewHolder(val binding: ItemWeekdayInformationBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(weather: WeatherDataApiResponse) {
        binding.weekdayForecast.text = weather.date
        binding.temperatureForecast.text =
            TemperatureFormatter.kelvinToCelsius(weather.main.temp).toString()
        binding.stateForecast.text = weather.weather.firstOrNull()?.description
//        binding.iconForecast.load("https://openweathermap.org/img/wn/01d@4x.png")
//
//        if(weather.weather.first().description == "nublado"){
//            // coloca nublado
//        }
//
    }

}
