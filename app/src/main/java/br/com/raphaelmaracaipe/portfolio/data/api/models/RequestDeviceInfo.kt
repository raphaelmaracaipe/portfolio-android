package br.com.raphaelmaracaipe.portfolio.data.api.models

import com.google.gson.Gson

data class RequestDeviceInfo(
    val keyToCommunication: String = ""
) {
    fun toJSON(): String = Gson().toJson(this)
}