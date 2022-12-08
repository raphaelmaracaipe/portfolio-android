package br.com.raphaelmaracaipe.portfolio.data.sp.token

import android.os.Build
import br.com.raphaelmaracaipe.portfolio.data.api.models.response.ResponseTokenModel
import br.com.raphaelmaracaipe.portfolio.utils.security.encryptDecrypt.EncryptDecryptImpl
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.M])
@RunWith(RobolectricTestRunner::class)
class TokenSPTest {

    private lateinit var tokenSP: TokenSP

    @Before
    fun setUp() {
        val context = RuntimeEnvironment.getApplication().applicationContext
        tokenSP = TokenSPImpl(context, EncryptDecryptImpl())
    }

    @Test
    fun `when register new token in sharedpreference`() {
        tokenSP.save(ResponseTokenModel("AAA", "BBB"))
        Assert.assertTrue(tokenSP.exist())
    }

    @Test
    fun `save value of key and encrypted e decrypted information`() {
        tokenSP.savedKeyOfCommunications("a")
        val keySaved = tokenSP.getKeyOfCommunication()
        Assert.assertEquals("a", keySaved)
    }

    @After
    fun after() {
        tokenSP.clearAll()
    }

}