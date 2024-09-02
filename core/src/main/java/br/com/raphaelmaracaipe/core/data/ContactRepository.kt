package br.com.raphaelmaracaipe.core.data

import br.com.raphaelmaracaipe.core.data.api.response.ContactResponse

interface ContactRepository {
    suspend fun consult(contacts: ArrayList<String>): ArrayList<ContactResponse>
}