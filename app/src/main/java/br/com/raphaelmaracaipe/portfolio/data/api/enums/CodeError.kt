package br.com.raphaelmaracaipe.portfolio.data.api.enums

enum class CodeError (val code: Int) {
    USER_EXIST_IN_DB(1000),
    USER_FAIL_REGISTER(1001),
    USER_EMAIL_INVALID(1002),
    USER_NOT_SEND_UUID(1003),
    USER_NOT_SAVED_CODE(1004),
    USER_CODE_NOT_VALID(1005),
    USER_CODE_EXPIRED(1006),
    USER_EMAIL_NOT_EXIST(1007),
    USER_ERROR_ON_REQUEST_TO_FORGOT(1008),
    USER_UUID_INVALID(1009),
    USER_NOT_EXIST(1010),
    USER_LOGIN_ANOTHER_METHOD(1011),
    USER_PASSWORD_INCORRECT(1012),
    GENERAL_ERROR(1)
}

fun translateIntegerToEnum(code: Int): CodeError = CodeError.values().find {
    it.code == code
} ?: CodeError.GENERAL_ERROR