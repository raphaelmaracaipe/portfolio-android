package br.com.raphaelmaracaipe.core.security

import android.os.Build
import br.com.raphaelmaracaipe.core.utils.Strings
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class CryptoHelperTest {

    private lateinit var cryptoHelper: CryptoHelper

    @Before
    fun setUp() {
        cryptoHelper = CryptoHelperImpl()
    }


    @Test
    fun `when you encrypt data should return text encrypted`() {
        val textToTest = "test"
        val key = Strings.generateStringRandom(16)
        val seed = Strings.generateStringRandom(16)

        val encrypted = cryptoHelper.encrypt(
            textToTest,
            key,
            seed
        )

        val decrypted = cryptoHelper.decrypt(
            encrypted,
            key,
            seed
        )

        assertEquals(textToTest, decrypted)
    }


}