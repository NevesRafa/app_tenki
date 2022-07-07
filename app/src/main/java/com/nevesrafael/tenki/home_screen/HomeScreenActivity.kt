package com.nevesrafael.tenki.home_screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nevesrafael.tenki.databinding.ActivityHomeScreenBinding

class HomeScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeScreenBinding
    private lateinit var presenter: HomeScreenPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = HomeScreenPresenter(this)

    }
}