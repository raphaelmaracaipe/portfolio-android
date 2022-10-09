package br.com.raphaelmaracaipe.portfolio.ui.userRegister.adapters

import android.graphics.PorterDuff
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import br.com.raphaelmaracaipe.portfolio.R

@BindingAdapter("checkPasswordValidImageView")
fun checkPasswordValidImageView(imageView: ImageView, isValid: Boolean) {
    val resColor = if (isValid) {
        R.color.green
    } else {
        R.color.colorWarning
    }

    val resDrawable = if(isValid) {
        R.drawable.ic_done
    } else {
        R.drawable.ic_close
    }

    val colorCompact = ContextCompat.getColor(imageView.context, resColor)
    val drawableCompact = ContextCompat.getDrawable(imageView.context, resDrawable)

    imageView.setColorFilter(colorCompact, PorterDuff.Mode.MULTIPLY)
    imageView.setImageDrawable(drawableCompact)
}

@BindingAdapter("checkPasswordValidTextView")
fun checkPasswordValidTextView(textView: TextView, isValid: Boolean) {
    val resColor = if (isValid) {
        R.color.green
    } else {
        R.color.colorWarning
    }

    val colorCompat = ContextCompat.getColor(textView.context, resColor)
    textView.setTextColor(colorCompat)
}