package br.com.raphaelmaracaipe.core.data.api

import br.com.raphaelmaracaipe.core.data.api.response.ContactResponse

interface ContactApi {
    suspend fun consult(contacts: ArrayList<String>): ArrayList<ContactResponse>
}