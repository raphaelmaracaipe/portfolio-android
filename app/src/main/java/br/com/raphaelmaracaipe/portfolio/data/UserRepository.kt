package br.com.raphaelmaracaipe.portfolio.data

import br.com.raphaelmaracaipe.portfolio.data.db.AppDataBase
import br.com.raphaelmaracaipe.portfolio.data.db.entities.UserEntity
import br.com.raphaelmaracaipe.portfolio.data.di.DataModule
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class UserRepository @Inject constructor(
    @DataModule.DataBase private val database: AppDataBase
) {

    suspend fun saveInDataBase(user: UserEntity) = withContext(Dispatchers.IO) {
        database.userDAO().insert(user)
    }

}