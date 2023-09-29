package br.com.raphaelmaracaipe.core.extensions

import java.nio.charset.Charset

fun Array<Byte>.convertToString(charset: Charset = Charsets.UTF_8) = String(
    this.toByteArray(), charset
)