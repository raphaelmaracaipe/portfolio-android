package br.com.raphaelmaracaipe.core.data.api

import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException

interface HandShakeApi {

    @Throws(NetworkException::class)
    suspend fun send(): String

}