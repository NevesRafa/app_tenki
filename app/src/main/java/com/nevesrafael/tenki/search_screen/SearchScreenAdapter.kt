package com.nevesrafael.tenki.search_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nevesrafael.tenki.databinding.ItemSavedLocationBinding
import com.nevesrafael.tenki.model.WeatherData

class SearchScreenAdapter() : RecyclerView.Adapter<SearchScreenViewHolder>() {

    val dataset = mutableListOf<WeatherData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchScreenViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSavedLocationBinding.inflate(inflater, parent, false)
        return SearchScreenViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchScreenViewHolder, position: Int) {
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

class SearchScreenViewHolder(val binding: ItemSavedLocationBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(dataset: WeatherData) {
        binding.location.text = dataset.localeName
    }
}


