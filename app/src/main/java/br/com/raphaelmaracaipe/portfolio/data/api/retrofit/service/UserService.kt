package br.com.raphaelmaracaipe.portfolio.data.api.retrofit.service

import br.com.raphaelmaracaipe.portfolio.BuildConfig
import br.com.raphaelmaracaipe.portfolio.models.ConsultEmail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {

    @Headers(
        "Accept: application/json; charset=utf-8",
        "x-api-key: ${BuildConfig.API_KEY}",
    )
    @GET("/api/v1/users/email/{email}")
    suspend fun userConsultEmail(@Path("email") email: String): Response<ConsultEmail>

}