package br.com.raphaelmaracaipe.core.data

import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse
import br.com.raphaelmaracaipe.core.data.sp.TokenSP
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TokenRepositoryImpl(
    private val tokenSP: TokenSP
) : TokenRepository {

    override fun isExistTokenRegistered(): Boolean = tokenSP.isExist()

    override fun getTokenRegistered(): TokensResponse = tokenSP.get()

    override suspend fun updateToken(): TokensResponse = withContext(Dispatchers.IO) {
        TokensResponse()
    }

}