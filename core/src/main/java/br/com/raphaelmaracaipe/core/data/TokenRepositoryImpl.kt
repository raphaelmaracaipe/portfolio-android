package br.com.raphaelmaracaipe.core.data

import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse
import br.com.raphaelmaracaipe.core.data.sp.TokenSP

class TokenRepositoryImpl(
    private val tokenSP: TokenSP
): TokenRepository {

    override fun isExistTokenRegistered(): Boolean = tokenSP.isExist()

    override fun getTokenRegistered(): TokensResponse = tokenSP.get()

}