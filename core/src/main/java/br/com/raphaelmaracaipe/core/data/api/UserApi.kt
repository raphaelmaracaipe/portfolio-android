package br.com.raphaelmaracaipe.core.data.api

import br.com.raphaelmaracaipe.core.data.api.response.UserSendCodeResponse

interface UserApi {
    @Throws(Exception::class)
    suspend fun sendCode(userSendCodeResponse: UserSendCodeResponse): Boolean
}