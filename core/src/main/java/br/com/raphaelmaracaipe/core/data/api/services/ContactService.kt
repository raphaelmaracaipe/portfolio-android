package br.com.raphaelmaracaipe.core.data.api.services

import br.com.raphaelmaracaipe.core.data.api.response.ContactResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ContactService {

    @Headers("Accept: application/json")
    @POST("/api/v1/contacts")
    suspend fun consult(@Body contacts: ArrayList<String>): Response<ArrayList<ContactResponse>>

}