package br.com.raphaelmaracaipe.core.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

object Images {

    fun transformBase64ToBitmap(base64: String): Bitmap? = try {
        val decoderBytes = Base64.decode(base64, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(decoderBytes, 0, decoderBytes.size)
    } catch (e: Exception) {
        null
    }

}