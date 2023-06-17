package br.com.raphaelmaracaipe.core.data.api.services

import br.com.raphaelmaracaipe.core.data.api.request.UserSendCodeRequest
import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {

    @Headers("Accept: application/json", "x-api-key: ca03na188ame03u1d78620de67282882a84")
    @POST("/api/v1/users/code")
    suspend fun sendCode(@Body userSendCodeRequest: UserSendCodeRequest): Response<Any>

    @Headers("Accept: application/json", "x-api-key: ca03na188ame03u1d78620de67282882a84")
    @GET("/api/v1/users/valid")
    suspend fun validCode(@Query("code") code: String): Response<TokensResponse>

}