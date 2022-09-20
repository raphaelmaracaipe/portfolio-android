package br.com.raphaelmaracaipe.portfolio.data

import br.com.raphaelmaracaipe.portfolio.data.api.di.ApiModule
import br.com.raphaelmaracaipe.portfolio.data.api.user.UserAPI
import br.com.raphaelmaracaipe.portfolio.data.db.AppDataBase
import br.com.raphaelmaracaipe.portfolio.data.db.di.DataBaseModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    @DataBaseModule.DataBase private val database: AppDataBase,
    @ApiModule.UserApi private val userAPI: UserAPI
) {

    suspend fun checkIfEmailExist(email: String) = withContext(Dispatchers.IO) {
        userAPI.checkIfEmailExist(email)
    }

}