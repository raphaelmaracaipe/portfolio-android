package br.com.raphaelmaracaipe.portfolio.utils.security.bcrypt

interface Bcrypt {

    fun crypt(text: String): String

    fun check(text: String, hash: String): Boolean
}