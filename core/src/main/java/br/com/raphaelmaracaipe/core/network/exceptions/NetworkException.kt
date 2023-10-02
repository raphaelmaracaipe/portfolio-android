package br.com.raphaelmaracaipe.core.network.exceptions

import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum

class NetworkException(val code: Int?) : Exception(code.toString()) {
    
    fun translateCodeError() = NetworkCodeEnum.findEnumUsingCodeError(code)
    
}