package br.com.raphaelmaracaipe.core.data.api.services

import br.com.raphaelmaracaipe.core.data.api.request.TokenRefreshRequest
import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface TokenInterceptorService {

    @Headers("Accept: application/json")
    @POST("/api/v1/tokens/refresh")
    suspend fun updateToken(
        @Body tokenRefreshRequest: TokenRefreshRequest
    ): Response<TokensResponse>

}