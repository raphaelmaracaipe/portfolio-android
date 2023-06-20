package br.com.raphaelmaracaipe.core.network

class NetworkException(val code: Int?) : Exception(code.toString()) {
    
    fun translateCodeError() = NetworkCodeEnum.findEnumUsingCodeError(code)
    
}