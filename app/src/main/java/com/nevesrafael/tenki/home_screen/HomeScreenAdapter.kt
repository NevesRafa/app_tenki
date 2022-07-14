package com.nevesrafael.tenki.home_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.nevesrafael.tenki.R
import com.nevesrafael.tenki.TemperatureFormatter
import com.nevesrafael.tenki.databinding.ItemWeekdayInformationBinding
import com.nevesrafael.tenki.formater.DateFormatter
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
        binding.weekdayForecast.text = DateFormatter.dateFormatter(weather.dateUnix)
        binding.temperatureForecast.text =
            TemperatureFormatter.kelvinToCelsius(weather.main.temp).toString()
        binding.stateForecast.text = weather.weather.firstOrNull()?.description

        val meuDrawable = when (weather.weather.first().icon) {
            "01d" -> R.drawable.clear_sky_day
            "01n" -> R.drawable.clear_sky_night
            "02d" -> R.drawable.few_clouds_day
            "02n" -> R.drawable.few_clouds_night
            "03d", "03n", "04d", "04n" -> R.drawable.scattered_clouds_day_night
            "09d", "09n" -> R.drawable.shower_rain_day_night
            "10d" -> R.drawable.rain_day
            "10n" -> R.drawable.rain_night
            "11d", "11n" -> R.drawable.thunderstorm_day_night
            "13d", "13n" -> R.drawable.snow
            "50d", "50n" -> R.drawable.mist
            else -> null
        }

        binding.iconForecast.load(meuDrawable)
    }



}
