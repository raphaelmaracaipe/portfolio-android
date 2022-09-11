package br.com.raphaelmaracaipe.portfolio.data

import br.com.raphaelmaracaipe.portfolio.data.db.AppDataBase
import br.com.raphaelmaracaipe.portfolio.data.db.di.DataBaseModule
import br.com.raphaelmaracaipe.portfolio.data.db.entities.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    @DataBaseModule.DataBase private val database: AppDataBase
) {

    suspend fun saveInDataBase(user: UserEntity) = withContext(Dispatchers.IO) {
        database.userDAO().insert(user)
    }

}