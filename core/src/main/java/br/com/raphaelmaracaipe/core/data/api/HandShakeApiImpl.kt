package br.com.raphaelmaracaipe.core.data.api

import br.com.raphaelmaracaipe.core.consts.REGEX_KEY
import br.com.raphaelmaracaipe.core.data.api.request.HandShakeRequest
import br.com.raphaelmaracaipe.core.data.api.services.HandShakeService
import br.com.raphaelmaracaipe.core.extensions.getCodeOfErrorBody
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException
import com.github.curiousoddman.rgxgen.RgxGen

class HandShakeApiImpl(
    private val handShakeService: HandShakeService
) : HandShakeApi {

    override suspend fun send(): String {
        val keyGenerated = RgxGen(REGEX_KEY).generate()
        val returnApi = handShakeService.send(HandShakeRequest(keyGenerated))

        if (!returnApi.isSuccessful) {
            throw NetworkException(returnApi.errorBody()?.getCodeOfErrorBody())
        }

        return keyGenerated
    }

}