package br.com.raphaelmaracaipe.portfolio.data.sp.token

import br.com.raphaelmaracaipe.portfolio.models.TokenModel

interface TokenSP {

    fun save(data: TokenModel)

    fun exist(): Boolean

}