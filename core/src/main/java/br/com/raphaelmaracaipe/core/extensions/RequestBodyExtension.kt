package br.com.raphaelmaracaipe.core.extensions

import okhttp3.RequestBody

fun RequestBody?.bodyToString(): String? = if (this == null) {
    ""
} else {
    val buffer = okio.Buffer()
    writeTo(buffer)
    buffer.readUtf8()
}