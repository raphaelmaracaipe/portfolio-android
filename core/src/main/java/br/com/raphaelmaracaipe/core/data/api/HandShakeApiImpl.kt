package br.com.raphaelmaracaipe.core.data.api

import android.util.Log
import br.com.raphaelmaracaipe.core.data.api.request.HandShakeRequest
import br.com.raphaelmaracaipe.core.data.api.services.HandShakeService

class HandShakeApiImpl(
    private val handShakeService: HandShakeService
): HandShakeApi {

    override suspend fun send(request: HandShakeRequest) {
        handShakeService.send(request)
    }

}