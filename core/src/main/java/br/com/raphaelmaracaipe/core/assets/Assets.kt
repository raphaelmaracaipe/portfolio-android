package br.com.raphaelmaracaipe.core.assets

import android.graphics.drawable.Drawable

interface Assets {
    fun read(location: String): String
    fun list(location: String): Array<String>
    fun drawableImage(locationImage: String): Drawable?
}