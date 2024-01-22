package br.com.raphaelmaracaipe.core.data.api

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.configRetrofitTest
import br.com.raphaelmaracaipe.core.data.api.request.ProfileRequest
import br.com.raphaelmaracaipe.core.data.api.request.UserSendCodeRequest
import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse
import br.com.raphaelmaracaipe.core.data.api.services.UserService
import br.com.raphaelmaracaipe.core.externals.KeysDefault
import br.com.raphaelmaracaipe.core.externals.NetworkUtils
import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum.*
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
class UserApiTest {

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
    fun `when send code to validation on the server and success`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(201).setBodyEncrypted("{}")
        )

        try {
            val isSendCode = userApi.sendCode(UserSendCodeRequest())
            assertTrue(isSendCode)
        } catch (_: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when send code to validation on the server and fail`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(500).setBodyEncrypted("{}")
        )

        try {
            userApi.sendCode(UserSendCodeRequest())
            assertTrue(false)
        } catch (e: NetworkException) {
            assertEquals(ERROR_GENERAL.code, e.code)
        } catch (_: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when request validation code and return success`() = runBlocking {
        val tokenResp = TokensResponse("aaa", "bbb")
        mockWebServer.enqueue(
            MockResponse().setResponseCode(201).setBodyEncrypted(tokenResp.toJSON())
        )

        try {
            val newToken = userApi.validCode("ccc")
            assertEquals(tokenResp.accessToken, newToken.accessToken)
        } catch (_: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when request validation code and return fail`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(500).setBody("{}")
        )

        try {
            userApi.validCode("ccc")
            assertTrue(false)
        } catch (e: NetworkException) {
            assertEquals(ERROR_GENERAL.code, e.code)
        } catch (_: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when request to send profile and return success`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(201).setBodyEncrypted("{}")
        )

        try {
            val isProfileSend = userApi.profile(ProfileRequest())
            assertTrue(isProfileSend)
        } catch (_: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when request to send profile and return fail`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(500).setBody("{}")
        )

        try {
            userApi.profile(ProfileRequest())
            assertTrue(false)
        } catch (e: NetworkException) {
            assertEquals(ERROR_GENERAL.code, e.code)
        }  catch (_: Exception) {
            assertTrue(false)
        }
    }

    @After
    fun after() {
        mockWebServer.shutdown()
    }

}