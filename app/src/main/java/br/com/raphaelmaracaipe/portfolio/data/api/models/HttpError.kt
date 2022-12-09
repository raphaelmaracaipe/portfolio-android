package br.com.raphaelmaracaipe.portfolio.data.api.models

import com.google.gson.Gson

data class HttpError(
    val message: String = "",
    val code: Int
) {
    fun toJSON(): String = Gson().toJson(this)
}