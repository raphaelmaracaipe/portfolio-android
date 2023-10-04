package br.com.raphaelmaracaipe.core.data.api

import br.com.raphaelmaracaipe.core.data.api.request.HandShakeRequest
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException
import kotlin.jvm.Throws

interface HandShakeApi {

    @Throws(NetworkException::class)
    suspend fun send(request: HandShakeRequest): Any?

}