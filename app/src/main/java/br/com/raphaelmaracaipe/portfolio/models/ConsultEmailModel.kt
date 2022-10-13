package br.com.raphaelmaracaipe.portfolio.models

import com.google.gson.Gson

data class ConsultEmailModel(
    val isExist: Boolean
) {
    fun toJSON() = Gson().toJson(this)
}