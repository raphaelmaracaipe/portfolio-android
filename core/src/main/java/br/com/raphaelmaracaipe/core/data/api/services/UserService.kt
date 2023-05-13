package br.com.raphaelmaracaipe.core.data.api.services

import br.com.raphaelmaracaipe.core.data.api.response.UserSendCodeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserService {

    @Headers("Accept: application/json", "x-api-key: ca03na188ame03u1d78620de67282882a84")
    @POST("/api/v1/users/code1")
    suspend fun sendCode(@Body userSendCodeResponse: UserSendCodeResponse): Response<Any>

}