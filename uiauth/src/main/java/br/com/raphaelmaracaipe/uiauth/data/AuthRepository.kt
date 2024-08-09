package br.com.raphaelmaracaipe.uiauth.data

interface AuthRepository {
    fun setPhone(phone: String)
    fun getPhone(): String
    fun checkIfIsPhoneSaved(): Boolean
}