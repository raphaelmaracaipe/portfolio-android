package br.com.raphaelmaracaipe.core.data.sp

import android.os.Build
import br.com.raphaelmaracaipe.core.TestApplication
import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse
import br.com.raphaelmaracaipe.core.externals.KeysDefault
import br.com.raphaelmaracaipe.core.externals.SpKeyDefault
import br.com.raphaelmaracaipe.core.security.CryptoHelperImpl
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class, sdk = [Build.VERSION_CODES.M])
class TokenSPTest {

    private lateinit var tokenSP: TokenSP

    @Before
    fun setUp() {
        val context = RuntimeEnvironment.getApplication().applicationContext
        val cryptoHelper = CryptoHelperImpl()
        val keysDefault = KeysDefault("nDHj82ZWov6r4bnu", "30rBgU6kuVSHPNXX")
        val spKeyDefault = SpKeyDefault("a", "b", "c", "d", "e", "f")

        tokenSP = TokenSPImpl(context, keysDefault, spKeyDefault, cryptoHelper)
    }

    @Test
    fun `when save value abount token should call method with data`() {
        tokenSP.save(TokensResponse("refresh", "access"))
        val tokens = tokenSP.get()
        assertEquals(tokens.accessToken, "access")
    }

    @Test
    fun `when save value abount token should call method without data`() {
        val tokens = tokenSP.get()
        assertEquals(tokens.accessToken, "")
    }

    @Test
    fun `when check if exist token saved should call method and return true or false`() {
        tokenSP.save(TokensResponse("refresh", "access"))
        assertTrue(tokenSP.isExist())
    }

}