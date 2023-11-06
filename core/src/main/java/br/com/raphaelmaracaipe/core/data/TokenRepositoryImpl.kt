package br.com.raphaelmaracaipe.core.data

import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse
import br.com.raphaelmaracaipe.core.data.sp.TokenSP
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TokenRepositoryImpl(
    private val tokenSP: TokenSP
) : TokenRepository {

    override suspend fun isExistTokenRegistered(): Boolean = withContext(Dispatchers.IO) {
        tokenSP.isExist()
    }

    override fun getTokenRegistered(): TokensResponse = tokenSP.get()

}