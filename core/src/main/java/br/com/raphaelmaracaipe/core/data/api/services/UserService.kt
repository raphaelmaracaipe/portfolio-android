package br.com.raphaelmaracaipe.core.data.api.services

import br.com.raphaelmaracaipe.core.data.api.request.ProfileRequest
import br.com.raphaelmaracaipe.core.data.api.request.UserSendCodeRequest
import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {

    @Headers("Accept: application/json")
    @POST("/api/v1/users/code")
    suspend fun sendCode(@Body userSendCodeRequest: UserSendCodeRequest): Response<Any>

    @Headers("Accept: application/json")
    @GET("/api/v1/users/valid")
    suspend fun validCode(@Query("code") code: String): Response<TokensResponse>

    @Headers("Accept: application/json")
    @POST("/api/v1/users/profile")
    suspend fun profile(@Body profileRequest: ProfileRequest): Response<Any>

}