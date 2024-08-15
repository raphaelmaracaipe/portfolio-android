package br.com.raphaelmaracaipe.core.data

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.data.api.TokenInterceptorApi
import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse
import br.com.raphaelmaracaipe.core.data.sp.TokenSP
import br.com.raphaelmaracaipe.core.utils.Strings
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class TokenRepositoryInterceptorTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private lateinit var tokenRepositoryInterceptor: TokenRepositoryInterceptor
    private lateinit var tokenSP: TokenSP
    private lateinit var tokenInterceptorApi: TokenInterceptorApi

    @Before
    fun setUp() {
        tokenSP = mockk()
        tokenInterceptorApi = mockk()

        tokenRepositoryInterceptor = TokenRepositoryInterceptorImpl(
            tokenSP,
            tokenInterceptorApi
        )
    }

    @Test
    fun `when check if exist token registered should return if exist`() {
        every { tokenSP.isExist() } returns true

        try {
            val isExistTokenRegistered = tokenRepositoryInterceptor.isExistTokenRegistered()
            assertTrue(isExistTokenRegistered)
        } catch (e: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when get token saved should return token or null saved`() {
        val tokenModel = TokensResponse("refresh", "access")
        every { tokenSP.get() } returns tokenModel

        try {
            val tokensSaved = tokenRepositoryInterceptor.getTokenRegistered()
            assertEquals(tokenModel.accessToken, tokensSaved.accessToken)
        } catch (e: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when update token and saved`() = runBlocking {
        val newToken = TokensResponse("refresh2", "access2")
        every { tokenSP.get() } returns TokensResponse("refresh", "access")
        every { tokenSP.save(any()) } returns Unit
        coEvery { tokenInterceptorApi.updateToken(any(), any(), any(), any()) } returns newToken

        try {
            val tokenReturns = tokenRepositoryInterceptor.updateToken(
                Strings.generateStringRandom(10),
                Strings.generateStringRandom(10),
                Strings.generateStringRandom(10)
            )
            assertEquals(newToken.accessToken, tokenReturns.accessToken)
        } catch (e: Exception) {
            assertTrue(false)
        }
    }

}