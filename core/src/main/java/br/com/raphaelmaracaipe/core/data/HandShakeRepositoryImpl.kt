package br.com.raphaelmaracaipe.core.data

import br.com.raphaelmaracaipe.core.data.api.HandShakeApi
import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HandShakeRepositoryImpl(
    private val handShakeApi: HandShakeApi
) : HandShakeRepository {

    override suspend fun send() = withContext(Dispatchers.IO) {
        try {
            handShakeApi.send()
        } catch (e: NetworkException) {
            throw NetworkException(e.code)
        } catch (e: Exception) {
            throw NetworkException(NetworkCodeEnum.ERROR_GENERAL.code)
        }
    }

}