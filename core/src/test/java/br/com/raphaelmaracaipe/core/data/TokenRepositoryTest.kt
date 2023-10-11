package br.com.raphaelmaracaipe.core.data

import android.os.Build
import br.com.raphaelmaracaipe.core.TestApplication
import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse
import br.com.raphaelmaracaipe.core.data.sp.TokenSP
import br.com.raphaelmaracaipe.core.data.sp.TokenSPImpl
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
class TokenRepositoryTest {

    private lateinit var tokenRepository: TokenRepository
    private lateinit var tokenSP: TokenSP

    @Before
    fun setUp() {
        val context = RuntimeEnvironment.getApplication().applicationContext
        val cryptoHelper = CryptoHelperImpl()
        val keysDefault = KeysDefault("nDHj82ZWov6r4bnu", "30rBgU6kuVSHPNXX")
        val spKeyDefault = SpKeyDefault("a", "b", "c", "d")

        tokenSP = TokenSPImpl(context, keysDefault, spKeyDefault, cryptoHelper)
        tokenRepository = TokenRepositoryImpl(tokenSP)
    }

    @Test
    fun `when get value token registered`() {
        tokenSP.save(TokensResponse("refresh", "access"))
        val tokens = tokenRepository.getTokenRegistered()
        assertEquals(tokens.accessToken, "access")
    }

    @Test
    fun `when check if exist token registered should return true or false`() {
        assertFalse(tokenRepository.isExistTokenRegistered())
    }

}