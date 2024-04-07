package br.com.raphaelmaracaipe.core.data

import br.com.raphaelmaracaipe.core.data.api.TokenInterceptorApi
import br.com.raphaelmaracaipe.core.data.api.request.TokenRefreshRequest
import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse
import br.com.raphaelmaracaipe.core.data.sp.TokenSP
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TokenRepositoryInterceptorImpl(
    private val tokenSP: TokenSP,
    private val tokenInterceptorApi: TokenInterceptorApi
) : TokenRepositoryInterceptor {

    override fun isExistTokenRegistered(): Boolean = tokenSP.isExist()

    override fun getTokenRegistered(): TokensResponse = tokenSP.get()

    override suspend fun updateToken(
        apiKey: String,
        seed: String,
        deviceId: String
    ) = withContext(Dispatchers.IO) {
        val tokensSaved = tokenSP.get()
        val tokenResponse = tokenInterceptorApi.updateToken(
            TokenRefreshRequest(tokensSaved.refreshToken),
            apiKey,
            seed,
            deviceId
        )
        tokenSP.save(tokenResponse)
        tokenResponse
    }

}