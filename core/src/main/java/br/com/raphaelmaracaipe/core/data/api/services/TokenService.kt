package br.com.raphaelmaracaipe.core.data.api.services

import br.com.raphaelmaracaipe.core.data.api.request.TokenRefreshRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface TokenService {

    @Headers("Accept: application/json")
    @POST("/api/v1/tokens/refresh")
    suspend fun updateToken(
        @Body tokenRefreshRequest: TokenRefreshRequest
    ): Response<Any>

}