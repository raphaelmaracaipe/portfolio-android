package br.com.raphaelmaracaipe.core.security

interface CryptoHelper {
    @Throws(Exception::class)
    fun decrypt(valueToDecrypt: String?, key: String?, iv: String?): String

    @Throws(Exception::class)
    fun encrypt(valueToEncrypt: String?, key: String?, iv: String?): String
}