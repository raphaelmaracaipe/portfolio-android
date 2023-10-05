package br.com.raphaelmaracaipe.core.data

import br.com.raphaelmaracaipe.core.data.api.HandShakeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HandShakeRepositoryImpl(
    private val api: HandShakeApi
) : HandShakeRepository {

    override suspend fun send() = withContext(Dispatchers.IO) {
        api.send()
    }

}