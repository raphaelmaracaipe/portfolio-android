package br.com.raphaelmaracaipe.core.data.api

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.TestApplication
import br.com.raphaelmaracaipe.core.data.DeviceRepositoryImpl
import br.com.raphaelmaracaipe.core.data.KeyRepositoryImpl
import br.com.raphaelmaracaipe.core.data.SeedRepositoryImpl
import br.com.raphaelmaracaipe.core.data.TokenRepositoryInterceptorApiImpl
import br.com.raphaelmaracaipe.core.data.api.response.ErrorResponse
import br.com.raphaelmaracaipe.core.data.api.services.HandShakeService
import br.com.raphaelmaracaipe.core.data.sp.DeviceIdSPImpl
import br.com.raphaelmaracaipe.core.data.sp.KeySPImpl
import br.com.raphaelmaracaipe.core.data.sp.SeedSPImpl
import br.com.raphaelmaracaipe.core.data.sp.TokenSPImpl
import br.com.raphaelmaracaipe.core.network.configRetrofit
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException
import br.com.raphaelmaracaipe.core.externals.ApiKeysDefault
import br.com.raphaelmaracaipe.core.externals.KeysDefault
import br.com.raphaelmaracaipe.core.externals.NetworkUtils
import br.com.raphaelmaracaipe.core.externals.SpKeyDefault
import br.com.raphaelmaracaipe.core.security.CryptoHelperImpl
import br.com.raphaelmaracaipe.core.utils.Strings.generateStringRandom
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
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class, sdk = [Build.VERSION_CODES.M])
class HandShakeApiTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val mockWebServer = MockWebServer()
    private lateinit var handShakeService: HandShakeService
    private lateinit var mContext: Context

    @Before
    fun setUp() {
        mContext = RuntimeEnvironment.getApplication().applicationContext

        mockWebServer.start()
        val baseURL = mockWebServer.url("").toString()
        NetworkUtils.URL_TO_MOCK = baseURL

        val cryptoHelper = CryptoHelperImpl()
        val keysDefault = KeysDefault("nDHj82ZWov6r4bnu", "30rBgU6kuVSHPNXX")
        val apiKeys = ApiKeysDefault(generateStringRandom(6), generateStringRandom(6))
        val spKeysDefault = SpKeyDefault(
            generateStringRandom(6),
            generateStringRandom(6),
            generateStringRandom(6),
            generateStringRandom(6),
            generateStringRandom(6),
            generateStringRandom(6),
            generateStringRandom(6),
            generateStringRandom(6)
        )

        val deviceIdSP = DeviceIdSPImpl(mContext, keysDefault, spKeysDefault, cryptoHelper)
        val keySp = KeySPImpl(mContext, keysDefault, spKeysDefault, cryptoHelper)
        val tokenSP = TokenSPImpl(mContext, keysDefault, spKeysDefault, cryptoHelper)
        val seedSP = SeedSPImpl(mContext)

        val deviceRepository = DeviceRepositoryImpl(deviceIdSP)
        val keyRepository = KeyRepositoryImpl(keySp, keysDefault)
        val seedRepository = SeedRepositoryImpl(seedSP)
        val tokenRepository = TokenRepositoryInterceptorApiImpl(tokenSP)

        handShakeService = configRetrofit(
            HandShakeService::class.java,
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
    fun `when send to server to register key of communication and return success`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200).setBody("{}")
        )

        val handShakeApi: HandShakeApi = HandShakeApiImpl(handShakeService)
        try {
            handShakeApi.send()
            assertTrue(true)
        } catch (e: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when send to server to register key of communication but return error`() = runBlocking {
        val errorResponse = ErrorResponse(401, 2001)
        mockWebServer.enqueue(
            MockResponse().setResponseCode(401).setBody(errorResponse.toJSON())
        )

        val handShakeApi: HandShakeApi = HandShakeApiImpl(handShakeService)
        try {
            handShakeApi.send()
            assertTrue(false)
        } catch (e: NetworkException) {
            assertEquals(errorResponse.codeError, e.code)
        }
    }

    @After
    fun after() {
        mockWebServer.shutdown()
    }

}
