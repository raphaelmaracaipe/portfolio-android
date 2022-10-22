package br.com.raphaelmaracaipe.portfolio.data

import br.com.raphaelmaracaipe.portfolio.data.api.di.ApiModule
import br.com.raphaelmaracaipe.portfolio.data.api.user.UserAPI
import br.com.raphaelmaracaipe.portfolio.data.db.AppDataBase
import br.com.raphaelmaracaipe.portfolio.data.db.di.DataBaseModule
import br.com.raphaelmaracaipe.portfolio.data.sp.di.SharedPreferenceModule
import br.com.raphaelmaracaipe.portfolio.data.sp.token.TokenSP
import br.com.raphaelmaracaipe.portfolio.ui.userRegister.models.UserRegisterModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    @DataBaseModule.DataBase private val database: AppDataBase,
    @ApiModule.UserApi private val userAPI: UserAPI,
    @SharedPreferenceModule.Token private val tokenSP: TokenSP
) {

    suspend fun checkIfEmailExist(email: String) = withContext(Dispatchers.IO) {
        userAPI.checkIfEmailExist(email)
    }

    suspend fun requestCode(email: String) = withContext(Dispatchers.IO) {
        userAPI.requestCode(email)
    }

    suspend fun registerUserInServer(data: UserRegisterModel) = withContext(Dispatchers.IO) {
        val token = userAPI.registerUserInServer(data)
        if(token.accessToken.isNotEmpty()) {
            tokenSP.save(token)
            true
        } else {
            false
        }
    }

    fun existTokenSaved() = tokenSP.exist()

}