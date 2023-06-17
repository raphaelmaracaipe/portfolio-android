package br.com.raphaelmaracaipe.core.data.api

import br.com.raphaelmaracaipe.core.data.api.request.UserSendCodeRequest
import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse

interface UserApi {

    @Throws(Exception::class)
    suspend fun sendCode(userSendCodeRequest: UserSendCodeRequest): Boolean

    @Throws(Exception::class)
    suspend fun validCode(code: String): TokensResponse

}