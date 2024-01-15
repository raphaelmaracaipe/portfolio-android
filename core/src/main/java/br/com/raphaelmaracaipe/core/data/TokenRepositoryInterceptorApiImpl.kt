package br.com.raphaelmaracaipe.core.data

import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse
import br.com.raphaelmaracaipe.core.data.sp.TokenSP

class TokenRepositoryInterceptorApiImpl(
    private val tokenSP: TokenSP
) : TokenRepositoryInterceptorApi {

    override fun isExistTokenRegistered(): Boolean = tokenSP.isExist()

    override fun getTokenRegistered(): TokensResponse = tokenSP.get()


}