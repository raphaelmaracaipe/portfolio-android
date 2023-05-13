package br.com.raphaelmaracaipe.core.data

import br.com.raphaelmaracaipe.core.data.api.UserApi
import br.com.raphaelmaracaipe.core.data.api.services.UserService
import br.com.raphaelmaracaipe.core.data.api.response.UserSendCodeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val api: UserApi
) : UserRepository {

    override suspend fun sendCode(userSendCode: UserSendCodeResponse) =
        withContext(Dispatchers.IO) {
            api.sendCode(userSendCode)
        }

}