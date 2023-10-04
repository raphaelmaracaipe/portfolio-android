package br.com.raphaelmaracaipe.core.data.api

import android.util.Log
import br.com.raphaelmaracaipe.core.data.api.request.HandShakeRequest
import br.com.raphaelmaracaipe.core.data.api.services.HandShakeService
import br.com.raphaelmaracaipe.core.extensions.getCodeOfErrorBody
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException

class HandShakeApiImpl(
    private val handShakeService: HandShakeService
): HandShakeApi {

    override suspend fun send(request: HandShakeRequest): Any? {
        val returnApi = handShakeService.send(request)
        if(!returnApi.isSuccessful) {
            throw NetworkException(returnApi.errorBody()?.getCodeOfErrorBody())
        }

        return returnApi.body()
    }

}