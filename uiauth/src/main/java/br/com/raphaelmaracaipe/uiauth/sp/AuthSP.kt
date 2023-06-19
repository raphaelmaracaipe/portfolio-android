package br.com.raphaelmaracaipe.uiauth.sp

interface AuthSP {

    fun setPhone(phone: String)

    fun getPhone(): String

    fun checkIfIsPhoneSaved(): Boolean

}