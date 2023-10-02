package br.com.raphaelmaracaipe.core.data.api.services

import br.com.raphaelmaracaipe.core.data.api.request.HandShakeRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface HandShakeService {

    @Headers("Accept: application/json")
    @POST("/api/v1/handshake")
    suspend fun send(@Body request: HandShakeRequest): Response<Any>

}