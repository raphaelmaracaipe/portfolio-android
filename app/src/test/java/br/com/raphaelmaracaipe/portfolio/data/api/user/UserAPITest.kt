package br.com.raphaelmaracaipe.portfolio.data.api.user

import android.content.Context
import android.os.Build
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.const.ConfigsToTest
import br.com.raphaelmaracaipe.portfolio.data.api.enums.CodeError.USER_EMAIL_INVALID
import br.com.raphaelmaracaipe.portfolio.data.api.models.HttpError
import br.com.raphaelmaracaipe.portfolio.data.api.retrofit.ConfigurationServiceImpl
import br.com.raphaelmaracaipe.portfolio.utils.device.DeviceNetworkImpl
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.json.JSONObject
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.M])
@RunWith(RobolectricTestRunner::class)
class UserAPITest {

    private val mockWebServer = MockWebServer()
    private lateinit var userAPI: UserAPI
    private lateinit var context: Context
    private var deviceNetworkImpl: DeviceNetworkImpl = mockk()

    @Before
    fun setUp() {
        mockWebServer.start()
        ConfigsToTest.urlToMock = mockWebServer.url("").toString()

        context = RuntimeEnvironment.getApplication().applicationContext

        userAPI = UserAPIImpl(
            context,
            ConfigurationServiceImpl(),
            deviceNetworkImpl
        )

        every { deviceNetworkImpl.isNetworkConnected() } answers { true }
    }

    @Test
    fun `when api response success should return information to user`() = runBlocking {
        val json = JSONObject()
        json.put("isExist", true)

        with(mockWebServer) {
            enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(json.toString())
            )
        }

        val returnAPI = userAPI.checkIfEmailExist("test@test.com")
        Assert.assertTrue(returnAPI)
    }

    @Test
    fun `when consult email valid but email return error of email invalid`() = runBlocking {
        val httpError = HttpError(message = "test", code = USER_EMAIL_INVALID.code)
        with(mockWebServer) {
            enqueue(
                MockResponse()
                    .setResponseCode(401)
                    .setBody(httpError.toJSON())
            )
        }

        try {
            userAPI.checkIfEmailExist("teste@test.com")
            Assert.assertTrue(false)
        } catch (e: Exception) {
            Assert.assertEquals(getRes(R.string.err_email_invalid), e.message)
        }
    }

    @Test
    fun `when consult email valid but email return error general`() = runBlocking {
        val httpError = HttpError(message = "test", code = 0)
        with(mockWebServer) {
            enqueue(
                MockResponse()
                    .setResponseCode(401)
                    .setBody(httpError.toJSON())
            )
        }

        try {
            userAPI.checkIfEmailExist("teste@test.com")
            Assert.assertTrue(false)
        } catch (e: Exception) {
            Assert.assertEquals(getRes(R.string.err_general_server), e.message)
        }
    }

    @Test
    fun `when consult email valid but device not have connection with internet`() = runBlocking {
        every { deviceNetworkImpl.isNetworkConnected() } answers { false }
        try {
            userAPI.checkIfEmailExist("teste@test.com")
            Assert.assertTrue(false)
        } catch (e: Exception) {
            Assert.assertEquals(getRes(R.string.err_not_connection_internet), e.message)
        }
    }

    private fun getRes(res: Int): String = context.resources.getString(res)

}