package br.com.raphaelmaracaipe.portfolio.data.api.models.request

data class RequestForgotPassword(
    var code: String = "",
    val email: String = ""
)
