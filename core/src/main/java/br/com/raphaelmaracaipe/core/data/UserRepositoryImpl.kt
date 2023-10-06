package br.com.raphaelmaracaipe.core.data

import br.com.raphaelmaracaipe.core.data.api.UserApi
import br.com.raphaelmaracaipe.core.data.api.request.UserSendCodeRequest
import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val api: UserApi
) : UserRepository {

    override suspend fun sendCode(userSendCode: UserSendCodeRequest) = withContext(Dispatchers.IO) {
        try {
            api.sendCode(userSendCode)
        } catch (e: NetworkException) {
            throw NetworkException(e.code)
        } catch (e: Exception) {
            throw NetworkException(NetworkCodeEnum.ERROR_GENERAL.code)
        }
    }

    override suspend fun validCode(code: String) = withContext(Dispatchers.IO) {
        try {
            api.validCode(code)
        } catch (e: NetworkException) {
            throw NetworkException(e.code)
        } catch (e: Exception) {
            throw NetworkException(NetworkCodeEnum.ERROR_GENERAL.code)
        }
    }

}