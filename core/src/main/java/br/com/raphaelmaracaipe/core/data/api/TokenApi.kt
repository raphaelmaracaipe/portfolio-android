package br.com.raphaelmaracaipe.core.data.api

import br.com.raphaelmaracaipe.core.data.api.request.TokenRefreshRequest
import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException

interface TokenApi {

    @Throws(NetworkException::class)
    suspend fun updateToken(request: TokenRefreshRequest): TokensResponse

}