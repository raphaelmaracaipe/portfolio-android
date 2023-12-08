package br.com.raphaelmaracaipe.core.extensions

import android.util.Base64

fun ByteArray.toBase64(): String? = Base64.encodeToString(
    this,
    Base64.DEFAULT
)