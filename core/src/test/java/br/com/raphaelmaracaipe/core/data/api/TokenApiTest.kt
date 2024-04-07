package br.com.raphaelmaracaipe.core.data.api

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.configRetrofitTest
import br.com.raphaelmaracaipe.core.data.api.request.TokenRefreshRequest
import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse
import br.com.raphaelmaracaipe.core.data.api.services.TokenService
import br.com.raphaelmaracaipe.core.externals.KeysDefault
import br.com.raphaelmaracaipe.core.externals.NetworkUtils
import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException
import br.com.raphaelmaracaipe.core.test.setBodyEncrypted
import kotlinx.coroutines.runBlocking
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class TokenApiTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val mockWebServer = MockWebServer()
    private lateinit var tokenService: TokenService
    private lateinit var tokenApi: TokenApi

    @Before
    fun setUp() {
        mockWebServer.start()
        NetworkUtils.URL_TO_MOCK = mockWebServer.url("").toString()

        val keysDefault = KeysDefault("nDHj82ZWov6r4bnu", "30rBgU6kuVSHPNXX")
        tokenService = configRetrofitTest(TokenService::class.java, keysDefault)
        tokenApi = TokenApiImpl(tokenService)
    }


    @Test
    fun `when call method to update token should return new tokens`() = runBlocking {
        val tokenResponseMocked = TokensResponse("aaa1", "bbb1")
        mockWebServer.enqueue(
            MockResponse().setResponseCode(201).setBodyEncrypted(tokenResponseMocked.toJSON())
        )

        try {
            val tokenResponse = tokenApi.updateToken(TokenRefreshRequest("aaa"))
            assertEquals(tokenResponseMocked.accessToken, tokenResponse.accessToken)
        } catch (_: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when call api to update token but return error`() = runBlocking {
        val tokenResponseMocked = TokensResponse("aaa1", "bbb1")
        mockWebServer.enqueue(
            MockResponse().setResponseCode(500).setBodyEncrypted("{}")
        )

        try {
            tokenApi.updateToken(TokenRefreshRequest("aaa"))
            assertTrue(false)
        } catch (e: NetworkException) {
            assertEquals(NetworkCodeEnum.ERROR_GENERAL.code, e.code)
        } catch (_: Exception) {
            assertTrue(false)
        }
    }

    @After
    fun after() {
        mockWebServer.shutdown()
    }
}