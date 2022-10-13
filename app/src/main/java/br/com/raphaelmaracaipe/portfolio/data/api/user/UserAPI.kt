package br.com.raphaelmaracaipe.portfolio.data.api.user

interface UserAPI {

    suspend fun checkIfEmailExist(email: String): Boolean

    suspend fun requestCode(): Boolean

}