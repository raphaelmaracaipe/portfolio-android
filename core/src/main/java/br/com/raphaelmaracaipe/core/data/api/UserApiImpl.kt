package br.com.raphaelmaracaipe.core.data.api

import br.com.raphaelmaracaipe.core.data.api.request.ProfileRequest
import br.com.raphaelmaracaipe.core.data.api.request.UserSendCodeRequest
import br.com.raphaelmaracaipe.core.data.api.response.ProfileGetResponse
import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse
import br.com.raphaelmaracaipe.core.data.api.services.UserService
import br.com.raphaelmaracaipe.core.extensions.getCodeOfErrorBody
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException

class UserApiImpl(
    private val userService: UserService
) : UserApi {

    override suspend fun sendCode(userSendCodeRequest: UserSendCodeRequest): Boolean {
        val returnHttp = userService.sendCode(userSendCodeRequest)
        if (!returnHttp.isSuccessful) {
            throw NetworkException(returnHttp.errorBody()?.getCodeOfErrorBody())
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

    override suspend fun profile(profile: ProfileRequest): Boolean {
        val returnHttp = userService.profile(profile)
        if (!returnHttp.isSuccessful) {
            throw NetworkException(returnHttp.errorBody()?.getCodeOfErrorBody())
        }
        return true
    }

    override suspend fun getProfile(): ProfileGetResponse {
        val returnHttp = userService.getProfile()
        if (!returnHttp.isSuccessful) {
            throw NetworkException(returnHttp.errorBody()?.getCodeOfErrorBody())
        }
        return returnHttp.body() ?: ProfileGetResponse()
    }

}