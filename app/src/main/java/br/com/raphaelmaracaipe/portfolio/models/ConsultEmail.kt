package br.com.raphaelmaracaipe.portfolio.models

import com.google.gson.Gson

data class ConsultEmail(
    val isExist: Boolean
) {
    fun toJSON() = Gson().toJson(this)
}