package br.com.raphaelmaracaipe.core.data.api

import br.com.raphaelmaracaipe.core.data.api.request.TokenRefreshRequest
import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse
import br.com.raphaelmaracaipe.core.data.api.services.TokenInterceptorService
import br.com.raphaelmaracaipe.core.extensions.getCodeOfErrorBody
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException

class TokenInterceptorApiImpl(
    private val tokenInterceptorService: TokenInterceptorService
) : TokenInterceptorApi {

    override suspend fun updateToken(request: TokenRefreshRequest): TokensResponse {
        val returnHttp = tokenInterceptorService.updateToken(request)
        if(!returnHttp.isSuccessful) {
            throw NetworkException(returnHttp.errorBody()?.getCodeOfErrorBody())
        }
        return returnHttp.body() ?: TokensResponse()
    }

}