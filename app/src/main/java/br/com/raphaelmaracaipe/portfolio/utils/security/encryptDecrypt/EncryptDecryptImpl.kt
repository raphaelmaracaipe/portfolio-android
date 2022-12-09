package br.com.raphaelmaracaipe.portfolio.utils.security.encryptDecrypt

import android.util.Base64
import java.io.UnsupportedEncodingException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.spec.InvalidKeySpecException
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.SecretKeySpec

class EncryptDecryptImpl : EncryptDecrypt {

    @Throws(
        NoSuchPaddingException::class,
        NoSuchAlgorithmException::class,
        InvalidKeyException::class,
        UnsupportedEncodingException::class,
        IllegalBlockSizeException::class,
        BadPaddingException::class
    )
    override fun encryptMessage(message: String, password: String): String {
        val secret = generateKey(password)

        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secret)
        val bytes = cipher.doFinal(message.toByteArray(charset("UTF-8")))

        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    @Throws(
        NoSuchPaddingException::class,
        NoSuchAlgorithmException::class,
        InvalidKeyException::class,
        IllegalBlockSizeException::class,
        BadPaddingException::class,
        UnsupportedEncodingException::class
    )
    override fun decryptMessage(textEncrypted: String, password: String): String {
        val secretKey = generateKey(password)

        val textInBytes = Base64.decode(textEncrypted, Base64.DEFAULT)
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, secretKey)

        return String(cipher.doFinal(textInBytes), charset("UTF-8"))
    }

    @Throws(
        NoSuchAlgorithmException::class, InvalidKeySpecException::class
    )
    private fun generateKey(password: String) = SecretKeySpec(password.toByteArray(), "AES")
}