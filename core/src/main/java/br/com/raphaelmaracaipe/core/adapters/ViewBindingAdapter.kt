package br.com.raphaelmaracaipe.core.adapters

import android.content.res.ColorStateList
import android.view.View
import androidx.databinding.BindingAdapter
import br.com.raphaelmaracaipe.core.utils.System.isNightMode

@BindingAdapter("visible")
fun View.visible(visible: Boolean) {
    visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter(value = ["backgroundColorLight", "backgroundColorDark"])
fun View.backgroundDarkOrLightMode(backgroundColorLight: Int, backgroundColorDark: Int) {
    val color = if (isNightMode(context)) {
        backgroundColorDark
    } else {
        backgroundColorLight
    }

    setBackgroundColor(color)
}

@BindingAdapter(value = ["backgroundTintColorLight", "backgroundTintColorDark"])
fun View.backgroundTintDarkOrLightMode(
    backgroundTintColorLight: Int,
    backgroundTintColorDark: Int
) {
    val color = if(isNightMode(context)) {
        backgroundTintColorDark
    } else {
        backgroundTintColorLight
    }

    backgroundTintList = ColorStateList.valueOf(color)
}