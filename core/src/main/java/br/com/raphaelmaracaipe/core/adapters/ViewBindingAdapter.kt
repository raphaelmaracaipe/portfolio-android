package br.com.raphaelmaracaipe.core.adapters

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visible")
fun View.visible(visible: Boolean) {
    visibility = if(visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}