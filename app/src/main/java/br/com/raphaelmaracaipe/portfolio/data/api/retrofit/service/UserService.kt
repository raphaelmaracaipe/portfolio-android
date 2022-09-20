package br.com.raphaelmaracaipe.portfolio.data.api.retrofit.service

import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserService {

    @POST("/api/v1/users")
    suspend fun userRegister(): Response<Void>

}