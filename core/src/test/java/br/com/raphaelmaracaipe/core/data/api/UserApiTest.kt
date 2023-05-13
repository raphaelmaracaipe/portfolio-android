package br.com.raphaelmaracaipe.core.data.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.FORBIDDEN
import br.com.raphaelmaracaipe.core.OK
import br.com.raphaelmaracaipe.core.data.api.response.UserSendCodeResponse
import br.com.raphaelmaracaipe.core.data.api.services.UserService
import br.com.raphaelmaracaipe.core.network.ConfigurationRetrofitUtils
import br.com.raphaelmaracaipe.core.network.configRetrofit
import kotlinx.coroutines.runBlocking
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
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
        ConfigurationRetrofitUtils.URL_TO_MOCK = baseURL

        userService = configRetrofit(UserService::class.java)
    }

    @Test
    fun `when send to server number to phone`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(OK).setBody("{}")
        )

        val userApi: UserApi = UserApiImpl(userService)
        val returnOfApi = userApi.sendCode(UserSendCodeResponse("99999999999"))
        assertTrue(returnOfApi)
    }

    @Test
    fun `when send to server number to phone but api return error`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(FORBIDDEN).setBody("{}")
        )

        val userApi: UserApi = UserApiImpl(userService)

        try {
            userApi.sendCode(UserSendCodeResponse("99999999999"))
            assertTrue(false)
        } catch (e: Exception) {
            assertEquals(null, e.message)
        }
    }

}