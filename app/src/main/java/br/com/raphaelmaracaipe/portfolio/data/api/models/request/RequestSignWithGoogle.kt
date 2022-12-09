package br.com.raphaelmaracaipe.portfolio.data.api.models.request

data class RequestSignWithGoogle(
    val email: String,
    var code: String = ""
)