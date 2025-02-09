package br.com.raphaelmaracaipe.core.data

interface AuthRepository {
    fun setPhone(phone: String)
    fun getPhone(): String
    fun checkIfIsPhoneSaved(): Boolean
}