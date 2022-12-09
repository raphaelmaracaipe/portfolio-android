package br.com.raphaelmaracaipe.portfolio.data.api.models.response

import com.google.gson.Gson

data class ResponseConsultEmailModel(
    val isExist: Boolean
) {
    fun toJSON() = Gson().toJson(this)
}