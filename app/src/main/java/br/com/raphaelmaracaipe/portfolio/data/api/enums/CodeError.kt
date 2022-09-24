package br.com.raphaelmaracaipe.portfolio.data.api.enums

enum class CodeError (val code: Int) {
    USER_EXIST_IN_DB(1000),
    USER_FAIL_REGISTER(1001),
    USER_EMAIL_INVALID(1002),
    GENERAL_ERROR(1)
}

fun translateIntegerToEnum(code: Int): CodeError = CodeError.values().find {
    it.code == code
} ?: CodeError.GENERAL_ERROR