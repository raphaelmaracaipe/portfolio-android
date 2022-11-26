package br.com.raphaelmaracaipe.portfolio.data.api.models

data class RequestSignWithGoogle(
    val email: String,
    val code: String,
    val deviceId: String
)