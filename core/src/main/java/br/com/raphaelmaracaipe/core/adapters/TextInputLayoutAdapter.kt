package br.com.raphaelmaracaipe.core.adapters

import android.content.res.ColorStateList
import androidx.databinding.BindingAdapter
import br.com.raphaelmaracaipe.core.utils.System
import com.google.android.material.textfield.TextInputLayout


@BindingAdapter(value = ["backgroundColorLight", "backgroundColorDark"])
fun TextInputLayout.backgroundDarkOrLightMode(backgroundColorLight: Int, backgroundColorDark: Int) {
    val color = if(System.isNightMode(context)) {
        backgroundColorDark
    } else {
        backgroundColorLight
    }

    boxStrokeColor = color
    hintTextColor = ColorStateList.valueOf(color)
}