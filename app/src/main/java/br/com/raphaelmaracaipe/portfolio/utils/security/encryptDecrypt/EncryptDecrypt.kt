package br.com.raphaelmaracaipe.portfolio.utils.security.encryptDecrypt

interface EncryptDecrypt {

    fun encryptMessage(message: String, password: String): String

    fun decryptMessage(textEncrypted: String, password: String): String

}