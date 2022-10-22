package br.com.raphaelmaracaipe.portfolio.data.api.user

import br.com.raphaelmaracaipe.portfolio.models.TokenModel
import br.com.raphaelmaracaipe.portfolio.ui.userRegister.models.UserRegisterModel
import retrofit2.Response

interface UserAPI {

    suspend fun checkIfEmailExist(email: String): Boolean

    suspend fun requestCode(email: String): Boolean

    suspend fun registerUserInServer(data: UserRegisterModel): TokenModel

}