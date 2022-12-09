package br.com.raphaelmaracaipe.portfolio.data.sp.token

import br.com.raphaelmaracaipe.portfolio.data.api.models.response.ResponseTokenModel

interface TokenSP {

    fun save(data: ResponseTokenModel)

    fun exist(): Boolean

    fun clearAll()

    fun savedKeyOfCommunications(key: String)

    fun getKeyOfCommunication(): String
}