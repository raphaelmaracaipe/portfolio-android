package br.com.raphaelmaracaipe.core.data.api

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.TestApplication
import br.com.raphaelmaracaipe.core.data.DeviceRepositoryImpl
import br.com.raphaelmaracaipe.core.data.KeyRepositoryImpl
import br.com.raphaelmaracaipe.core.data.SeedRepositoryImpl
import br.com.raphaelmaracaipe.core.data.TokenRepositoryInterceptorApiImpl
import br.com.raphaelmaracaipe.core.data.api.request.TokenRefreshRequest
import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse
import br.com.raphaelmaracaipe.core.data.api.services.TokenService
import br.com.raphaelmaracaipe.core.data.sp.DeviceIdSP
import br.com.raphaelmaracaipe.core.data.sp.DeviceIdSPImpl
import br.com.raphaelmaracaipe.core.data.sp.KeySP
import br.com.raphaelmaracaipe.core.data.sp.KeySPImpl
import br.com.raphaelmaracaipe.core.data.sp.SeedSP
import br.com.raphaelmaracaipe.core.data.sp.SeedSPImpl
import br.com.raphaelmaracaipe.core.data.sp.TokenSP
import br.com.raphaelmaracaipe.core.data.sp.TokenSPImpl
import br.com.raphaelmaracaipe.core.externals.ApiKeysDefault
import br.com.raphaelmaracaipe.core.externals.KeysDefault
import br.com.raphaelmaracaipe.core.externals.NetworkUtils
import br.com.raphaelmaracaipe.core.externals.SpKeyDefault
import br.com.raphaelmaracaipe.core.network.configRetrofit
import br.com.raphaelmaracaipe.core.security.CryptoHelperImpl
import br.com.raphaelmaracaipe.core.test.setBodyEncrypted
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
class TokenApiTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val mockWebServer = MockWebServer()
    private lateinit var keysDefault: KeysDefault
    private lateinit var tokenService: TokenService
    private lateinit var mContext: Context
    private lateinit var deviceIdSP: DeviceIdSP
    private lateinit var keySp: KeySP
    private lateinit var tokenSP: TokenSP
    private lateinit var seedSp: SeedSP

    @Before
    fun setUp() {
        mockWebServer.start()
        val baseURL = mockWebServer.url("").toString()
        NetworkUtils.URL_TO_MOCK = baseURL

        keysDefault = KeysDefault("nDHj82ZWov6r4bnu", "30rBgU6kuVSHPNXX")
        val cryptoHelper = CryptoHelperImpl()
        val apiKeys = ApiKeysDefault(
            generateStringRandom(6),
            generateStringRandom(6)
        )
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

        mContext = RuntimeEnvironment.getApplication().applicationContext
        deviceIdSP = DeviceIdSPImpl(mContext, keysDefault, spKeysDefault, cryptoHelper)
        keySp = KeySPImpl(mContext, keysDefault, spKeysDefault, cryptoHelper)
        tokenSP = TokenSPImpl(mContext, keysDefault, spKeysDefault, cryptoHelper)
        seedSp = SeedSPImpl(mContext)

        val deviceRepository = DeviceRepositoryImpl(deviceIdSP)
        val keyRepository = KeyRepositoryImpl(keySp, keysDefault)
        val seedRepository = SeedRepositoryImpl(seedSp)
        val tokenRepository = TokenRepositoryInterceptorApiImpl(tokenSP)

        tokenService = configRetrofit(
            TokenService::class.java,
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
    fun `when update token should call to server and return success`() = runBlocking {
        val tokenResponse = TokensResponse("aaa", "bbb")

        mockWebServer.enqueue(
            MockResponse().setResponseCode(200).setBodyEncrypted(tokenResponse.toJSON())
        )

        val tokenApi: TokenApi = TokenApiImpl(tokenService)
        try {
            val returnTokensUpdated = tokenApi.updateToken(TokenRefreshRequest("CCC"))
            assertEquals(tokenResponse.accessToken, returnTokensUpdated.accessToken)
        } catch (e: Exception) {
            assertTrue(false)
        }
    }

    @After
    fun after() {
        mockWebServer.shutdown()
    }

}