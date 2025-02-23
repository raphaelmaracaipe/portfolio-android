package br.com.raphaelmaracaipe.core.adapters

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.databinding.BindingAdapter
import br.com.raphaelmaracaipe.core.utils.Images
import com.google.android.material.imageview.ShapeableImageView

@BindingAdapter(
    value = ["imageBitmapAD", "imageBase64", "imageResourceIfNotExistBitmap"], requireAll = false
)
fun ShapeableImageView.setImage(
    image: Bitmap? = null,
    imageInBase64: String? = null,
    imageResourceIfNotExistBitmap: Drawable? = null
) {
    image?.let {
        setImageBitmap(image)
        return
    }

    imageInBase64?.let {
        val bitmap = Images.transformBase64ToBitmap(it)
        setImageBitmap(bitmap)
        return
    }

    imageResourceIfNotExistBitmap?.let {
        setImageDrawable(it)
    }
}