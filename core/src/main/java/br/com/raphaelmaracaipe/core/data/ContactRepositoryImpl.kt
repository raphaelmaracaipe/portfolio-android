package br.com.raphaelmaracaipe.core.data

import br.com.raphaelmaracaipe.core.data.api.ContactApi
import br.com.raphaelmaracaipe.core.data.db.daos.ContactDAO
import br.com.raphaelmaracaipe.core.data.db.entities.ContactEntity
import br.com.raphaelmaracaipe.core.extensions.copyOf
import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException

class ContactRepositoryImpl(
    private val contactApi: ContactApi,
    private val contactDAO: ContactDAO
) : ContactRepository {

    override suspend fun consult(contacts: ArrayList<String>) = try {
        val contactsConsulted = contactApi.consult(contacts)

        val contactsEntityCopied: ArrayList<ContactEntity> = arrayListOf()
        contactsEntityCopied.copyOf(contactsConsulted)
        contactDAO.insert(contactsEntityCopied)

        contactsConsulted
    } catch (e: NetworkException) {
        e.printStackTrace()
        throw NetworkException(e.code)
    } catch (e: Exception) {
        e.printStackTrace()
        throw NetworkException(NetworkCodeEnum.ERROR_GENERAL.code)
    }

}