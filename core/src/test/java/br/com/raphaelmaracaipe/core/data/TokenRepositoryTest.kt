package br.com.raphaelmaracaipe.core.data

import android.os.Build
import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse
import br.com.raphaelmaracaipe.core.data.sp.TokenSP
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class TokenRepositoryTest {

    private lateinit var tokenRepository: TokenRepository
    private lateinit var tokenSP: TokenSP

    @Before
    fun setUp() {
        tokenSP = mockk()
        tokenRepository = TokenRepositoryImpl(tokenSP)
    }

    @Test
    fun `when check if exist token should retruns`() {
        every { tokenSP.isExist() } returns true
        try {
            val isExist = tokenRepository.isExistTokenRegistered()
            assertTrue(isExist)
        } catch (e: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when saved token`() {
        val token = TokensResponse("aa1", "bb1")
        every { tokenSP.get() } returns token
        try {
            val tokensSaved = tokenRepository.getTokenRegistered()
            assertEquals(token.accessToken, tokensSaved.accessToken)
        } catch (e: Exception) {
            assertTrue(false)
        }
    }

}