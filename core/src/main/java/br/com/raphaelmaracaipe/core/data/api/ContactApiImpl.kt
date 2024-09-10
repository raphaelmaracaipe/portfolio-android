package br.com.raphaelmaracaipe.core.data.api

import br.com.raphaelmaracaipe.core.data.api.response.ContactResponse
import br.com.raphaelmaracaipe.core.data.api.services.ContactService
import br.com.raphaelmaracaipe.core.extensions.getCodeOfErrorBody
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException

class ContactApiImpl(
    private val contactService: ContactService
) : ContactApi {
    override suspend fun consult(contacts: ArrayList<String>): ArrayList<ContactResponse> {
        val returnApi = contactService.consult(contacts)
        if (!returnApi.isSuccessful) {
            throw NetworkException(returnApi.errorBody()?.getCodeOfErrorBody())
        }

        return returnApi.body() ?: arrayListOf()
    }
}