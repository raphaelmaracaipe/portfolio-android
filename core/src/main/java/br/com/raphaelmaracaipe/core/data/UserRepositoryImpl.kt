package br.com.raphaelmaracaipe.core.data

import br.com.raphaelmaracaipe.core.data.api.UserApi
import br.com.raphaelmaracaipe.core.data.api.request.UserSendCodeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val api: UserApi
) : UserRepository {

    override suspend fun sendCode(userSendCode: UserSendCodeRequest) =
        withContext(Dispatchers.IO) {
            api.sendCode(userSendCode)
        }

    override suspend fun validCode(code: String) =
        withContext(Dispatchers.IO) {
            api.validCode(code)
        }

}