package br.com.raphaelmaracaipe.core.adapters

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.databinding.BindingAdapter
import com.google.android.material.imageview.ShapeableImageView

@BindingAdapter("imageBitmapAD", "imageResourceIfNotExistBitmap")
fun ShapeableImageView.setImage(image: Bitmap? = null, imageResourceIfNotExistBitmap: Drawable? = null) {
    image?.let {
        setImageBitmap(image)
        return
    }
    imageResourceIfNotExistBitmap?.let {
        setImageDrawable(it)
    }
}