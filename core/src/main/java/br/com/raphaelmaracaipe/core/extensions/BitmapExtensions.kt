package br.com.raphaelmaracaipe.core.extensions

import android.graphics.Bitmap
import android.util.Base64
import android.util.Base64.encodeToString
import java.io.ByteArrayOutputStream

fun Bitmap.toBase64(): String? {
    val byteArrayOutputStream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return encodeToString(byteArray, Base64.DEFAULT)
}

fun Bitmap.toByteArray(): ByteArray {
    val stream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.toByteArray()
}