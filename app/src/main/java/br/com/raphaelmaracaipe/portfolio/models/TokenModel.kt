package br.com.raphaelmaracaipe.portfolio.models

import com.google.gson.Gson

data class TokenModel(
    val accessToken: String = "",
    val refreshToken: String = ""
) {
    fun toJSON(): String = Gson().toJson(this)
}
