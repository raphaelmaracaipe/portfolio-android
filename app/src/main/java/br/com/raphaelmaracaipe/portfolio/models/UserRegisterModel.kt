package br.com.raphaelmaracaipe.portfolio.models

data class UserRegisterModel(
    val email: String = "", var password: String = "", var code: String = ""
)
