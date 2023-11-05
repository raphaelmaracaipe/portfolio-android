package br.com.raphaelmaracaipe.core.data

import br.com.raphaelmaracaipe.core.data.api.request.UserSendCodeRequest
import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse

interface UserRepository {
    suspend fun sendCode(userSendCode: UserSendCodeRequest): Boolean

    suspend fun validCode(code: String)
}