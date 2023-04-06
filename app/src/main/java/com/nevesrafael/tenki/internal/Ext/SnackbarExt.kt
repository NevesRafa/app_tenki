package com.nevesrafael.tenki.internal.Ext

import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.nevesrafael.tenki.R


fun Snackbar.setErrorStyle(): Snackbar = apply {
    val errorBgColor = ContextCompat.getColor(context, R.color.red)
    setBackgroundTint(errorBgColor)
}