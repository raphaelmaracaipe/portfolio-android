package br.com.raphaelmaracaipe.core.data

import br.com.raphaelmaracaipe.core.data.sp.AuthSP

class AuthRepositoryImpl(
    private val authSP: AuthSP
) : AuthRepository {

    override fun setPhone(phone: String) {
        authSP.setPhone(phone)
    }

    override fun getPhone() = authSP.getPhone()

    override fun checkIfIsPhoneSaved() = authSP.getPhone().isNotEmpty()

}