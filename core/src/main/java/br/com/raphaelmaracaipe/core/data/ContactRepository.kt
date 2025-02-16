package br.com.raphaelmaracaipe.core.data

import br.com.raphaelmaracaipe.core.data.db.entities.ContactEntity

interface ContactRepository {
    suspend fun consult(contacts: ArrayList<String>): ArrayList<ContactEntity>
    suspend fun contactSaved(limitPerPage: Int, currentPage: Int): ArrayList<ContactEntity>
    suspend fun isExistSaved(phone: String): Boolean
    suspend fun searchItem(
        text: String, itemPerPage: Int, pageCurrent: Int
    ): ArrayList<ContactEntity>
    suspend fun lastSeen(phone: String)
}