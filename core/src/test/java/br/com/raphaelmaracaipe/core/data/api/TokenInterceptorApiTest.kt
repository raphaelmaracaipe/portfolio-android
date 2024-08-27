package br.com.raphaelmaracaipe.core.data.api

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.configRetrofitTest
import br.com.raphaelmaracaipe.core.data.api.request.TokenRefreshRequest
import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse
import br.com.raphaelmaracaipe.core.data.api.services.TokenInterceptorService
import br.com.raphaelmaracaipe.core.externals.KeysDefault
import br.com.raphaelmaracaipe.core.externals.NetworkUtils
import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum.ERROR_GENERAL
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException
import br.com.raphaelmaracaipe.core.test.setBodyEncrypted
import br.com.raphaelmaracaipe.core.utils.Strings
import kotlinx.coroutines.runBlocking
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import org.junit.After
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
class TokenInterceptorApiTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val mockWebServer = MockWebServer()
    private lateinit var tokenInterceptorApi: TokenInterceptorApi

    @Before
    fun setUp() {
        mockWebServer.start()
        NetworkUtils.URL_TO_MOCK = mockWebServer.url("").toString()

        val keysDefault = KeysDefault("nDHj82ZWov6r4bnu", "30rBgU6kuVSHPNXX")
        val tokenInterceptorService = configRetrofitTest(
            TokenInterceptorService::class.java, keysDefault
        )
        tokenInterceptorApi = TokenInterceptorApiImpl(tokenInterceptorService)
    }

    @Test
    fun `when update token and return success`() = runBlocking {
        val tokenResponse = TokensResponse(
            Strings.generateStringRandom(5), Strings.generateStringRandom(5)
        )
        mockWebServer.enqueue(
            MockResponse().setResponseCode(201).setBodyEncrypted(tokenResponse.toJSON())
        )

        try {
            val tokenReturn = tokenInterceptorApi.updateToken(
                TokenRefreshRequest("ddd"),
                Strings.generateStringRandom(5),
                Strings.generateStringRandom(5),
                Strings.generateStringRandom(5)
            )
            assertEquals(tokenResponse.accessToken, tokenReturn.accessToken)
        } catch (_: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when update token and return fail`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(500).setBodyEncrypted("{}")
        )

        try {
            tokenInterceptorApi.updateToken(
                TokenRefreshRequest("ddd"),
                Strings.generateStringRandom(5),
                Strings.generateStringRandom(5),
                Strings.generateStringRandom(5)
            )
            assertTrue(false)
        } catch (e: NetworkException) {
            assertEquals(ERROR_GENERAL.code, e.code)
        } catch (_: Exception) {
            assertTrue(false)
        }
    }

    @After
    fun after() {
        mockWebServer.shutdown()
    }
}