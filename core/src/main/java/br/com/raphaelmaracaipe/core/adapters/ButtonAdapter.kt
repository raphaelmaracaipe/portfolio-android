package br.com.raphaelmaracaipe.core.adapters

import android.widget.Button
import androidx.databinding.BindingAdapter
import br.com.raphaelmaracaipe.core.utils.System

@BindingAdapter(value = ["backgroundColorLight", "backgroundColorDark"])
fun Button.backgroundTintDarkOrLightMode(
    backgroundTintColorLight: Int,
    backgroundTintColorDark: Int
) {
    val color = if(System.isNightMode(context)) {
        backgroundTintColorDark
    } else {
        backgroundTintColorLight
    }

    setTextColor(color)
}