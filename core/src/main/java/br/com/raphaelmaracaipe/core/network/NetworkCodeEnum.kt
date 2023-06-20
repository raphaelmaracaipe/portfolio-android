package br.com.raphaelmaracaipe.core.network

enum class NetworkCodeEnum(val code: Int) {
    ERROR_GENERAL(1000),
    USER_SEND_CODE_PHONE_NOT_VALID(2000),
    USER_SEND_CODE_INVALID(2001);

    companion object {
        fun findEnumUsingCodeError(code: Int?) = NetworkCodeEnum.values().find {
            it.code == code
        } ?: ERROR_GENERAL
    }

}