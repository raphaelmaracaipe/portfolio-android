package br.com.raphaelmaracaipe.core.data.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.data.api.request.UserSendCodeRequest
import br.com.raphaelmaracaipe.core.data.api.response.ErrorResponse
import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse
import br.com.raphaelmaracaipe.core.data.api.services.UserService
import br.com.raphaelmaracaipe.core.network.utils.NetworkUtils
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException
import br.com.raphaelmaracaipe.core.network.configRetrofit
import kotlinx.coroutines.runBlocking
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserApiTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val mockWebServer = MockWebServer()
    private lateinit var userService: UserService

    @Before
    fun setUp() {
        mockWebServer.start()
        val baseURL = mockWebServer.url("").toString()
        NetworkUtils.URL_TO_MOCK = baseURL

        userService = configRetrofit(UserService::class.java)
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
        mockWebServer.enqueue(
            MockResponse().setResponseCode(201).setBody(tokens.toJSON())
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