package br.com.raphaelmaracaipe.core.data

import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse

interface TokenRepositoryInterceptor {
    fun isExistTokenRegistered(): Boolean
    fun getTokenRegistered(): TokensResponse
    suspend fun updateToken(apiKey: String, seed: String, deviceId: String): TokensResponse
}