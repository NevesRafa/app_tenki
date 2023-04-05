package com.nevesrafael.tenki.internal.formater

import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {

    fun dateFormatter(unixSeconds: Long): String {

        val date = Date(unixSeconds * 1000L)
        val sdf = SimpleDateFormat("EEEE")

        return sdf.format(date)

    }
}
