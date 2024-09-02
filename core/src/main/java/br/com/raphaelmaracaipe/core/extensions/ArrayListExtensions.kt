package br.com.raphaelmaracaipe.core.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> ArrayList<T>.copyOf(source: Any) {
    val gson = Gson()
    val jsonOfSource = gson.toJson(source)

    val type = object : TypeToken<ArrayList<T>>() {}.type
    val arrayListCopied: ArrayList<T> = gson.fromJson(jsonOfSource, type)

    this.addAll(arrayListCopied)
}