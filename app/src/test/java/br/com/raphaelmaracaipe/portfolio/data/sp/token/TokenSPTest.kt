package br.com.raphaelmaracaipe.portfolio.data.sp.token

import android.os.Build
import br.com.raphaelmaracaipe.portfolio.models.TokenModel
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
        tokenSP = TokenSPImpl(context)
    }

    @Test
    fun `when register new token in sharedpreference`() {
        tokenSP.save(TokenModel("AAA", "BBB"))
        Assert.assertTrue(tokenSP.exist())
    }

}