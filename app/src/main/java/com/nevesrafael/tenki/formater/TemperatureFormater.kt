package com.nevesrafael.tenki

import kotlin.math.roundToInt

object TemperatureFormatter {

    fun kelvinToCelsius(tempKelvin: Double): Int {

        val tempCelcius = (tempKelvin - 273.15)

        return tempCelcius.roundToInt()
    }
}


