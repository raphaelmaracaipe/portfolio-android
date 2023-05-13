package br.com.raphaelmaracaipe.core.data.api

import br.com.raphaelmaracaipe.core.data.api.response.UserSendCodeResponse
import br.com.raphaelmaracaipe.core.data.api.services.UserService

class UserApiImpl(
    private val userService: UserService
): UserApi {

    override suspend fun sendCode(userSendCodeResponse: UserSendCodeResponse): Boolean {
        val returnHttp = userService.sendCode(userSendCodeResponse)
        if(!returnHttp.isSuccessful) {
            throw Exception()
        }
        return true
    }

}