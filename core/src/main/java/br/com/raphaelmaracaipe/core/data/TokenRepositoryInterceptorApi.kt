package br.com.raphaelmaracaipe.core.data

import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse

interface TokenRepositoryInterceptorApi {
    fun isExistTokenRegistered(): Boolean
    fun getTokenRegistered(): TokensResponse
}