package br.com.raphaelmaracaipe.core.data.api

import br.com.raphaelmaracaipe.core.data.api.request.ProfileRequest
import br.com.raphaelmaracaipe.core.data.api.request.UserSendCodeRequest
import br.com.raphaelmaracaipe.core.data.api.response.ProfileGetResponse
import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException

interface UserApi {

    @Throws(Exception::class)
    suspend fun sendCode(userSendCodeRequest: UserSendCodeRequest): Boolean

    @Throws(NetworkException::class)
    suspend fun validCode(code: String): TokensResponse

    @Throws(NetworkException::class)
    suspend fun profile(profile: ProfileRequest): Boolean

    @Throws(NetworkException::class)
    suspend fun getProfile(): ProfileGetResponse

}