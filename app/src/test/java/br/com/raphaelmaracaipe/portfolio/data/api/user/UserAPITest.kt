package br.com.raphaelmaracaipe.portfolio.data.api.user

import android.content.Context
import android.os.Build
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.const.ConfigsToTest
import br.com.raphaelmaracaipe.portfolio.data.api.enums.CodeError
import br.com.raphaelmaracaipe.portfolio.data.api.enums.CodeError.*
import br.com.raphaelmaracaipe.portfolio.data.api.models.HttpError
import br.com.raphaelmaracaipe.portfolio.data.api.retrofit.ConfigurationServiceImpl
import br.com.raphaelmaracaipe.portfolio.data.sp.device.DeviceSP
import br.com.raphaelmaracaipe.portfolio.data.sp.device.DeviceSPImpl
import br.com.raphaelmaracaipe.portfolio.models.TokenModel
import br.com.raphaelmaracaipe.portfolio.ui.userRegister.models.UserRegisterModel
import br.com.raphaelmaracaipe.portfolio.utils.device.DeviceNetwork
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
        val deviceNetwork: DeviceNetwork = DeviceNetworkImpl(context)
        val deviceSP: DeviceSP = DeviceSPImpl(context)

        userAPI = UserAPIImpl(
            context,
            ConfigurationServiceImpl(deviceNetwork),
            deviceSP
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

    @Test
    fun `when register user but device not have connection with internet`() = runBlocking {
        every { deviceNetworkImpl.isNetworkConnected() } answers { false }
        try {
            userAPI.registerUserInServer(UserRegisterModel())
            Assert.assertTrue(false)
        } catch (e: Exception) {
            Assert.assertEquals(getRes(R.string.err_not_connection_internet), e.message)
        }
    }

    @Test
    fun `when register user in api and return with success`() = runBlocking {
        every { deviceNetworkImpl.isNetworkConnected() } answers { true }

        val token = TokenModel(accessToken = "AAA", refreshToken = "BBB")
        with(mockWebServer) {
            enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(token.toJSON())
            )
        }

        val tokenModel = userAPI.registerUserInServer(UserRegisterModel())
        Assert.assertEquals("BBB", tokenModel.refreshToken)
    }

    @Test
    fun `when register user in api but email is invalid`() = runBlocking {
        every { deviceNetworkImpl.isNetworkConnected() } answers { true }

        val httpError = HttpError(message = "", code = USER_EMAIL_INVALID.code)
        with(mockWebServer) {
            enqueue(
                MockResponse()
                    .setResponseCode(401)
                    .setBody(httpError.toJSON())
            )
        }

        try {
            userAPI.registerUserInServer(UserRegisterModel())
            Assert.assertTrue(false)
        } catch (e: Exception) {
            Assert.assertEquals(getRes(R.string.err_email_invalid), e.message)
        }
    }

    @Test
    fun `when user register in api but code is invalid`() = runBlocking {
        every { deviceNetworkImpl.isNetworkConnected() } answers { true }

        val httpError = HttpError(message = "", code = USER_CODE_NOT_VALID.code)
        with(mockWebServer) {
            enqueue(
                MockResponse()
                    .setResponseCode(401)
                    .setBody(httpError.toJSON())
            )
        }

        try {
            userAPI.registerUserInServer(UserRegisterModel())
            Assert.assertTrue(false)
        } catch (e: Exception) {
            Assert.assertEquals(getRes(R.string.reg_code_invalid), e.message)
        }
    }

    @Test
    fun `when user register in api but email already in db`() = runBlocking {
        every { deviceNetworkImpl.isNetworkConnected() } answers { true }

        val httpError = HttpError(message = "", code = USER_EXIST_IN_DB.code)
        with(mockWebServer) {
            enqueue(
                MockResponse()
                    .setResponseCode(401)
                    .setBody(httpError.toJSON())
            )
        }

        try {
            userAPI.registerUserInServer(UserRegisterModel())
            Assert.assertTrue(false)
        } catch (e: Exception) {
            Assert.assertEquals(getRes(R.string.reg_error_email_exist), e.message)
        }
    }

    @Test
    fun `when user register in api but code is expired`() = runBlocking {
        every { deviceNetworkImpl.isNetworkConnected() } answers { true }

        val httpError = HttpError(message = "", code = USER_CODE_EXPIRED.code)
        with(mockWebServer) {
            enqueue(
                MockResponse()
                    .setResponseCode(401)
                    .setBody(httpError.toJSON())
            )
        }

        try {
            userAPI.registerUserInServer(UserRegisterModel())
            Assert.assertTrue(false)
        } catch (e: Exception) {
            Assert.assertEquals(getRes(R.string.reg_erro_code_expired_title), e.message)
        }
    }

    @Test
    fun `when user register in api but happen error general`() = runBlocking {
        every { deviceNetworkImpl.isNetworkConnected() } answers { true }

        val httpError = HttpError(message = "", code = 0)
        with(mockWebServer) {
            enqueue(
                MockResponse()
                    .setResponseCode(401)
                    .setBody(httpError.toJSON())
            )
        }

        try {
            userAPI.registerUserInServer(UserRegisterModel())
            Assert.assertTrue(false)
        } catch (e: Exception) {
            Assert.assertEquals(getRes(R.string.err_general_server), e.message)
        }
    }

    @Test
    fun `when request code and api return with success`() = runBlocking {
        every { deviceNetworkImpl.isNetworkConnected() } answers { true }

        with(mockWebServer) {
            enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody("")
            )
        }

        Assert.assertTrue(userAPI.requestCode("teste@teste.com"))
    }

    @Test
    fun `when request code but device is not have connection with internet`() = runBlocking {
        every { deviceNetworkImpl.isNetworkConnected() } answers { false }
        try {
            userAPI.requestCode("teste@teste.com")
            Assert.assertTrue(false)
        } catch (e: Exception) {
            Assert.assertEquals(getRes(R.string.err_not_connection_internet), e.message)
        }
    }

    @Test
    fun `when request code and happen error general`() = runBlocking {
        every { deviceNetworkImpl.isNetworkConnected() } answers { true }

        val httpError = HttpError(message = "", code = 0)
        with(mockWebServer) {
            enqueue(
                MockResponse()
                    .setResponseCode(401)
                    .setBody(httpError.toJSON())
            )
        }

        try {
            userAPI.registerUserInServer(UserRegisterModel())
            Assert.assertTrue(false)
        } catch (e: Exception) {
            Assert.assertEquals(getRes(R.string.err_general_server), e.message)
        }
    }

    private fun getRes(res: Int): String = context.resources.getString(res)

}