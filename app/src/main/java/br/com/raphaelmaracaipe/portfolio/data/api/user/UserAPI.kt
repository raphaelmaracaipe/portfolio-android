package br.com.raphaelmaracaipe.portfolio.data.api.user

import br.com.raphaelmaracaipe.portfolio.models.TokenModel
import br.com.raphaelmaracaipe.portfolio.models.UserRegisterModel

interface UserAPI {

    suspend fun checkIfEmailExist(email: String): Boolean

    suspend fun requestCode(email: String): Boolean

    suspend fun registerUserInServer(data: UserRegisterModel): TokenModel

}