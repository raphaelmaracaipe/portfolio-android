package br.com.raphaelmaracaipe.core.data

import br.com.raphaelmaracaipe.core.data.api.request.HandShakeRequest

interface HandShakeRepository {
    suspend fun send(request: HandShakeRequest): Any?
}