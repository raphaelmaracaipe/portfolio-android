package br.com.raphaelmaracaipe.portfolio.data.sp.userPassword

interface UserPassword {

    fun save(text: String)

    fun get(): String

}