package br.com.raphaelmaracaipe.core.network.interceptors

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.TestApplication
import br.com.raphaelmaracaipe.core.data.DeviceRepositoryImpl
import br.com.raphaelmaracaipe.core.data.KeyRepositoryImpl
import br.com.raphaelmaracaipe.core.data.SeedRepositoryImpl
import br.com.raphaelmaracaipe.core.data.TokenRepositoryInterceptorApiImpl
import br.com.raphaelmaracaipe.core.data.api.TokenApi
import br.com.raphaelmaracaipe.core.data.api.TokenApiImpl
import br.com.raphaelmaracaipe.core.data.api.request.TokenRefreshRequest
import br.com.raphaelmaracaipe.core.data.api.response.ErrorResponse
import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse
import br.com.raphaelmaracaipe.core.data.api.services.TokenInterceptorService
import br.com.raphaelmaracaipe.core.data.sp.DeviceIdSPImpl
import br.com.raphaelmaracaipe.core.data.sp.KeySPImpl
import br.com.raphaelmaracaipe.core.data.sp.SeedSPImpl
import br.com.raphaelmaracaipe.core.data.sp.TokenSPImpl
import br.com.raphaelmaracaipe.core.externals.ApiKeysDefault
import br.com.raphaelmaracaipe.core.externals.KeysDefault
import br.com.raphaelmaracaipe.core.externals.NetworkUtils
import br.com.raphaelmaracaipe.core.externals.SpKeyDefault
import br.com.raphaelmaracaipe.core.network.configRetrofit
import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum.*
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException
import br.com.raphaelmaracaipe.core.security.CryptoHelperImpl
import br.com.raphaelmaracaipe.core.test.setBodyEncrypted
import br.com.raphaelmaracaipe.core.utils.Strings
import kotlinx.coroutines.runBlocking
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class, sdk = [Build.VERSION_CODES.M])
class DecryptedInterceptorTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val mockWebServer = MockWebServer()
    private lateinit var tokenService: TokenInterceptorService


    @Before
    fun setUp() {
        mockWebServer.start()
        val baseURL = mockWebServer.url("").toString()
        NetworkUtils.URL_TO_MOCK = baseURL

        val keysDefault = KeysDefault("nDHj82ZWov6r4bnu", "30rBgU6kuVSHPNXX")
        val cryptoHelper = CryptoHelperImpl()
        val apiKeys = ApiKeysDefault(
            Strings.generateStringRandom(6),
            Strings.generateStringRandom(6)
        )
        val spKeysDefault = SpKeyDefault(
            Strings.generateStringRandom(6),
            Strings.generateStringRandom(6),
            Strings.generateStringRandom(6),
            Strings.generateStringRandom(6),
            Strings.generateStringRandom(6),
            Strings.generateStringRandom(6),
            Strings.generateStringRandom(6),
            Strings.generateStringRandom(6)
        )

        val context = RuntimeEnvironment.getApplication().applicationContext
        val deviceIdSP = DeviceIdSPImpl(context, keysDefault, spKeysDefault, cryptoHelper)
        val keySp = KeySPImpl(context, keysDefault, spKeysDefault, cryptoHelper)
        val tokenSP = TokenSPImpl(context, keysDefault, spKeysDefault, cryptoHelper)
        val seedSp = SeedSPImpl(context)

        val deviceRepository = DeviceRepositoryImpl(deviceIdSP)
        val keyRepository = KeyRepositoryImpl(keySp, keysDefault)
        val seedRepository = SeedRepositoryImpl(seedSp)
        val tokenRepository = TokenRepositoryInterceptorApiImpl(tokenSP)

        tokenService = configRetrofit(
            TokenInterceptorService::class.java,
            cryptoHelper,
            keysDefault,
            apiKeys,
            deviceRepository,
            keyRepository,
            seedRepository,
            tokenRepository
        )
    }

    @Test
    fun `when make request and processing encrypted datas`() = runBlocking {
        val tokenResponse = TokensResponse("aaa", "bbb")
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200).setBodyEncrypted(tokenResponse.toJSON())
        )

        val tokenApi: TokenApi = TokenApiImpl(tokenService)
        try {
            val returnTokensUpdated = tokenApi.updateToken(TokenRefreshRequest("a"))
            assertEquals(tokenResponse.accessToken, returnTokensUpdated.accessToken)
        } catch (_: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when make request but return error 500 should take care response`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(500).setBodyEncrypted("{}")
        )

        val tokenApi: TokenApi = TokenApiImpl(tokenService)
        try {
            tokenApi.updateToken(TokenRefreshRequest("a"))
            assertTrue(false)
        } catch (e: NetworkException) {
            assertEquals(ERROR_GENERAL.code, e.code)
        } catch (_: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when make request not encrypted should down in catch and return body empty`() =
        runBlocking {
            mockWebServer.enqueue(
                MockResponse().setResponseCode(201).setBody("{}")
            )

            val tokenApi: TokenApi = TokenApiImpl(tokenService)
            try {
                tokenApi.updateToken(TokenRefreshRequest("a"))
                assertTrue(true)
            } catch (_: Exception) {
                assertTrue(false)
            }
        }

    @Test
    fun `when receiver error of api 403 but code not is update token should return error generic`() =
        runBlocking {
            val errorResponse = ErrorResponse(403, ERROR_GENERAL.code)
            mockWebServer.enqueue(
                MockResponse().setResponseCode(403).setBody(errorResponse.toJSON())
            )

            val tokenApi: TokenApi = TokenApiImpl(tokenService)
            try {
                tokenApi.updateToken(TokenRefreshRequest("a"))
                assertTrue(false)
            } catch (e: NetworkException) {
                assertTrue(false)
            } catch (_: Exception) {
                assertTrue(true)
            }
        }

    @Test
    fun `a`() = runBlocking {
        val errorResponse = ErrorResponse(403, TOKEN_INVALID.code)
        mockWebServer.enqueue(
            MockResponse().setResponseCode(403).setBody(errorResponse.toJSON())
        )

        val tokenApi: TokenApi = TokenApiImpl(tokenService)
        try {
            tokenApi.updateToken(TokenRefreshRequest("a"))
            assertTrue(false)
        } catch (e: NetworkException) {
            assertTrue(false)
        } catch (_: Exception) {
            assertTrue(true)
        }
    }


}