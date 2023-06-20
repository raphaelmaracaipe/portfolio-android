package br.com.raphaelmaracaipe.core.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> String.fromJSON(): T {
    val itemType = object : TypeToken<T> () {}.type
    return Gson().fromJson(this, itemType)
}