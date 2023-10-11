package br.com.raphaelmaracaipe.core.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.security.MessageDigest

inline fun <reified T> String.fromJSON(): T {
    val itemType = object : TypeToken<T> () {}.type
    return Gson().fromJson(this, itemType)
}

fun String.toSha1() = MessageDigest
    .getInstance("SHA-1")
    .digest(this.toByteArray())
    .joinToString(separator = "", transform = { "%02x".format(it) })