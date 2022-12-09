package br.com.raphaelmaracaipe.portfolio.data.api.retrofit.service

import br.com.raphaelmaracaipe.portfolio.BuildConfig
import br.com.raphaelmaracaipe.portfolio.data.api.models.request.RequestCodeModel
import br.com.raphaelmaracaipe.portfolio.data.api.models.request.RequestForgotPassword
import br.com.raphaelmaracaipe.portfolio.data.api.models.request.RequestSignWithGoogle
import br.com.raphaelmaracaipe.portfolio.data.api.models.response.ResponseConsultEmailModel
import br.com.raphaelmaracaipe.portfolio.data.api.models.response.ResponseTokenModel
import br.com.raphaelmaracaipe.portfolio.models.UserRegisterModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {

    @Headers(
        "Accept: application/json; charset=utf-8",
        "x-api-key: ${BuildConfig.API_KEY}",
    )
    @GET("/api/v1/users/email/{email}")
    suspend fun userConsultEmail(@Path("email") email: String): Response<ResponseConsultEmailModel>

    @Headers(
        "Accept: application/json; charset=utf-8",
        "x-api-key: ${BuildConfig.API_KEY}",
    )
    @POST("/api/v1/users/code")
    suspend fun requestCode(@Body dataToBody: RequestCodeModel): Response<Void>

    @Headers(
        "Accept: application/json; charset=utf-8",
        "x-api-key: ${BuildConfig.API_KEY}",
    )
    @POST("/api/v1/users")
    suspend fun registerUserInServer(@Body dataToBody: UserRegisterModel): Response<ResponseTokenModel>

    @Headers(
        "Accept: application/json; charset=utf-8",
        "x-api-key: ${BuildConfig.API_KEY}",
    )
    @POST("/api/v1/users/google")
    suspend fun signWithGoogle(@Body dataToBody: RequestSignWithGoogle): Response<ResponseTokenModel>

    @Headers(
        "Accept: application/json; charset=utf-8",
        "x-api-key: ${BuildConfig.API_KEY}",
    )
    @POST("/api/v1/users/login")
    suspend fun login(@Body body: UserRegisterModel): Response<ResponseTokenModel>

    @Headers(
        "Accept: application/json; charset=utf-8",
        "x-api-key: ${BuildConfig.API_KEY}",
    )
    @PUT("/api/v1/users/forgot")
    suspend fun forgotPassword(@Body body: RequestForgotPassword): Response<Void>

}