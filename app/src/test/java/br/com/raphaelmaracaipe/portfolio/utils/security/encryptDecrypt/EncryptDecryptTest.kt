package br.com.raphaelmaracaipe.portfolio.utils.security.encryptDecrypt

import android.os.Build
import br.com.raphaelmaracaipe.portfolio.BuildConfig
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class EncryptDecryptTest {

    private val encryptDecrypt: EncryptDecrypt = EncryptDecryptImpl()

    @Test
    fun `when encrypt and decrypt text`() {
        val textPlain = "text of encrypt"
        val textEncrypted = encryptDecrypt.encryptMessage(textPlain, BuildConfig.KEY)
        val textDecrypted = encryptDecrypt.decryptMessage(textEncrypted, BuildConfig.KEY)
        Assert.assertEquals(textPlain, textDecrypted)
    }

}