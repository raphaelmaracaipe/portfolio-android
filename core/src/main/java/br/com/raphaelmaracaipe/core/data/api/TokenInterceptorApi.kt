package br.com.raphaelmaracaipe.core.data.api

import br.com.raphaelmaracaipe.core.data.api.request.TokenRefreshRequest
import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse

interface TokenInterceptorApi {
    suspend fun updateToken(
        request: TokenRefreshRequest,
        apiKey: String,
        seed: String,
        deviceId: String
    ): TokensResponse
}