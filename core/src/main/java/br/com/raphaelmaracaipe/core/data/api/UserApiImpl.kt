package br.com.raphaelmaracaipe.core.data.api

import br.com.raphaelmaracaipe.core.data.api.request.UserSendCodeRequest
import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse
import br.com.raphaelmaracaipe.core.data.api.services.UserService
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException
import br.com.raphaelmaracaipe.core.extensions.getCodeOfErrorBody

class UserApiImpl(
    private val userService: UserService
) : UserApi {

    override suspend fun sendCode(userSendCodeRequest: UserSendCodeRequest): Boolean {
        val returnHttp = userService.sendCode(userSendCodeRequest)
        if (!returnHttp.isSuccessful) {
            throw Exception()
        }
        return true
    }

    override suspend fun validCode(code: String): TokensResponse {
        val returnHttp = userService.validCode(code)
        if (!returnHttp.isSuccessful) {
            throw NetworkException(returnHttp.errorBody()?.getCodeOfErrorBody())
        }
        return returnHttp.body() ?: TokensResponse()
    }

}