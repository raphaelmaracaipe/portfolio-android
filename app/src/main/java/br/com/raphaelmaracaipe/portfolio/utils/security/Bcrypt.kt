package br.com.raphaelmaracaipe.portfolio.utils.security

interface Bcrypt {

    fun crypt(text: String): String

}