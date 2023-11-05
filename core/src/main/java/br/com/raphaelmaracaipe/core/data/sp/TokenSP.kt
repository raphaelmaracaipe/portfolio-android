package br.com.raphaelmaracaipe.core.data.sp

import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse

interface TokenSP {
    fun isExist(): Boolean

    fun get(): TokensResponse

    fun save(tokens: TokensResponse)
}