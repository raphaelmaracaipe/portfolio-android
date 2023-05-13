package br.com.raphaelmaracaipe.core.data

import br.com.raphaelmaracaipe.core.data.api.response.UserSendCodeResponse
import retrofit2.Response

interface UserRepository {
    suspend fun sendCode(userSendCode: UserSendCodeResponse): Boolean
}