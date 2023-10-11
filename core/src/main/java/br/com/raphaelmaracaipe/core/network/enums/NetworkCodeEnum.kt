package br.com.raphaelmaracaipe.core.network.enums

enum class NetworkCodeEnum(val code: Int) {
    ERROR_GENERAL(1000),
    USER_SEND_CODE_PHONE_NOT_VALID(2000),
    USER_SEND_CODE_INVALID(2001),
    USER_SEND_DEVICE_ID_INVALID(2003),
    USER_KEY_INVALID(2004),
    DEVICE_ID_INVALID(3001),
    SEED_INVALID(4001);

    companion object {
        fun findEnumUsingCodeError(code: Int?) = NetworkCodeEnum.values().find {
            it.code == code
        } ?: ERROR_GENERAL
    }

}