package br.com.raphaelmaracaipe.portfolio.data.api.models.response

import com.google.gson.Gson

data class ResponseTokenModel(
    val accessToken: String = "",
    val refreshToken: String = ""
) {
    fun toJSON(): String = Gson().toJson(this)
}
