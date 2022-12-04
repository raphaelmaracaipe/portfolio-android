package br.com.raphaelmaracaipe.portfolio.data.api.models

data class RequestForgotPassword(
    val code: String = "",
    val email: String = ""
)
