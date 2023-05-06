package br.com.raphaelmaracaipe.data.api

import br.com.raphaelmaracaipe.models.ResponseCodeCountry
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface PhoneApi {

    @Headers("Accept: application/json", "x-api-key: ca03na188ame03u1d78620de67282882a84")
    @GET("/api/v1/consultCodePhone")
    suspend fun getCodeOfCountry(): Response<Array<ResponseCodeCountry>>

}