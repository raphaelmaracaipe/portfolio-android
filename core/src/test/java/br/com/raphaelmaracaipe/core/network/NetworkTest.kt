package br.com.raphaelmaracaipe.core.network

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.configRetrofitTest
import br.com.raphaelmaracaipe.core.data.api.UserApi
import br.com.raphaelmaracaipe.core.data.api.UserApiImpl
import br.com.raphaelmaracaipe.core.data.api.response.ErrorResponse
import br.com.raphaelmaracaipe.core.data.api.response.ProfileGetResponse
import br.com.raphaelmaracaipe.core.data.api.services.UserService
import br.com.raphaelmaracaipe.core.externals.KeysDefault
import br.com.raphaelmaracaipe.core.externals.NetworkUtils
import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum.ERROR_GENERAL
import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum.TOKEN_INVALID
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException
import br.com.raphaelmaracaipe.core.test.setBodyEncrypted
import br.com.raphaelmaracaipe.core.utils.Strings
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class NetworkTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val mockWebServer = MockWebServer()
    private lateinit var userApi: UserApi

    @Before
    fun setUp() {
        mockWebServer.start()
        NetworkUtils.URL_TO_MOCK = mockWebServer.url("").toString()

        val keysDefault = KeysDefault("nDHj82ZWov6r4bnu", "30rBgU6kuVSHPNXX")
        val userService = configRetrofitTest(UserService::class.java, keysDefault)
        userApi = UserApiImpl(userService)
    }

    @Test
    fun `when call api but return error 403 (token invalid)`() = runBlocking {
        val jsonError = Gson().toJson(
            ErrorResponse(codeError = TOKEN_INVALID.code, statusCode = 403)
        )
        mockWebServer.enqueue(
            MockResponse().setResponseCode(403).setBody(jsonError)
        )

        try {
            userApi.getProfile()
            assertTrue(false)
        } catch (e: Exception) {
            assertTrue(true)
        }
    }

    @Test
    fun `when call api but return error 403`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(403).setBodyEncrypted("{}")
        )

        try {
            userApi.getProfile()
            assertTrue(false)
        } catch (e: Exception) {
            assertTrue(true)
        }
    }

    @Test
    fun `when call api but return error 500`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(500).setBodyEncrypted("{}")
        )

        try {
            userApi.getProfile()
            assertTrue(false)
        } catch (e: NetworkException) {
            assertEquals(ERROR_GENERAL.code, e.code)
        }
    }

    @Test
    fun `when call api but error occurs in parse json`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200).setBody(getJSONResponse())
        )

        try {
            val profile = userApi.getProfile()
            assertEquals("", profile.name)
        } catch (e: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when call api return success`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(201).setBodyEncrypted(getJSONResponse())
        )

        try {
            val profile = userApi.getProfile()
            assertEquals(2, profile.name.length)
        } catch (e: Exception) {
            assertTrue(false)
        }
    }

    private fun getJSONResponse() = Gson().toJson(
        ProfileGetResponse(
            Strings.generateStringRandom(2),
            Strings.generateStringRandom(20),
        )
    )

    @After
    fun after() {
        mockWebServer.shutdown()
    }

}