package br.com.raphaelmaracaipe.core.data.api

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.TestApplication
import br.com.raphaelmaracaipe.core.data.DeviceRepositoryImpl
import br.com.raphaelmaracaipe.core.data.KeyRepositoryImpl
import br.com.raphaelmaracaipe.core.data.SeedRepositoryImpl
import br.com.raphaelmaracaipe.core.data.api.request.UserSendCodeRequest
import br.com.raphaelmaracaipe.core.data.api.response.ErrorResponse
import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse
import br.com.raphaelmaracaipe.core.data.api.services.UserService
import br.com.raphaelmaracaipe.core.data.sp.DeviceIdSPImpl
import br.com.raphaelmaracaipe.core.data.sp.KeySPImpl
import br.com.raphaelmaracaipe.core.data.sp.SeedSPImpl
import br.com.raphaelmaracaipe.core.network.configRetrofit
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException
import br.com.raphaelmaracaipe.core.externals.ApiKeysDefault
import br.com.raphaelmaracaipe.core.externals.KeysDefault
import br.com.raphaelmaracaipe.core.externals.NetworkUtils
import br.com.raphaelmaracaipe.core.externals.SpKeyDefault
import br.com.raphaelmaracaipe.core.security.CryptoHelperImpl
import br.com.raphaelmaracaipe.core.utils.encryptedBodyRequest
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
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class, sdk = [Build.VERSION_CODES.M])
class UserApiTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val mockWebServer = MockWebServer()
    private lateinit var userService: UserService
    private lateinit var mContext: Context

    @Before
    fun setUp() {
        mContext = RuntimeEnvironment.getApplication().applicationContext

        mockWebServer.start()
        val baseURL = mockWebServer.url("").toString()
        NetworkUtils.URL_TO_MOCK = baseURL

        val cryptoHelper = CryptoHelperImpl()
        val keysDefault = KeysDefault("nDHj82ZWov6r4bnu", "30rBgU6kuVSHPNXX")
        val spKeysDefault = SpKeyDefault("AAA", "AAA", "AAA", "AAA")
        val apiKeys = ApiKeysDefault("AAA", "BBB")

        val deviceIdSP = DeviceIdSPImpl(mContext, keysDefault, spKeysDefault, cryptoHelper)
        val keySp = KeySPImpl(mContext, keysDefault, cryptoHelper)
        val seedSp = SeedSPImpl(mContext)

        val deviceRepository = DeviceRepositoryImpl(deviceIdSP)
        val keyRepository = KeyRepositoryImpl(keySp, keysDefault)
        val seedRepository = SeedRepositoryImpl(seedSp)

        userService = configRetrofit(
            UserService::class.java,
            cryptoHelper,
            keysDefault,
            apiKeys,
            deviceRepository,
            keyRepository,
            seedRepository
        )
    }

    @Test
    fun `when send to server number to phone`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(201).setBody("{}")
        )

        val userApi: UserApi = UserApiImpl(userService)
        val returnOfApi = userApi.sendCode(UserSendCodeRequest("99999999999"))
        assertTrue(returnOfApi)
    }

    @Test
    fun `when send to server number to phone but api return error`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(403).setBody("{}")
        )

        val userApi: UserApi = UserApiImpl(userService)
        try {
            userApi.sendCode(UserSendCodeRequest("99999999999"))
            assertTrue(false)
        } catch (e: Exception) {
            assertEquals(null, e.message)
        }
    }

    @Test
    fun `when send code correct and return token`() = runBlocking {
        val tokens = TokensResponse("a", "b")

        val bodyEncrypted = encryptedBodyRequest(tokens.toJSON())
        mockWebServer.enqueue(
            MockResponse().setResponseCode(201).setBody(bodyEncrypted)
        )

        try {
            val userApi: UserApi = UserApiImpl(userService)
            val tokensResponse = userApi.validCode("1")
            assertEquals(tokens.accessToken, tokensResponse.accessToken)
        } catch (e: NetworkException) {
            assertTrue(false)
        }
    }

    @Test
    fun `when send code incorrect`() = runBlocking {
        val errorResponse = ErrorResponse(401, 2001)
        mockWebServer.enqueue(
            MockResponse().setResponseCode(401).setBody(errorResponse.toJSON())
        )

        try {
            val userApi: UserApi = UserApiImpl(userService)
            userApi.validCode("1")
            assertTrue(false)
        } catch (e: NetworkException) {
            assertEquals(2001, e.code)
        }
    }

    @After
    fun after() {
        mockWebServer.shutdown()
    }
}