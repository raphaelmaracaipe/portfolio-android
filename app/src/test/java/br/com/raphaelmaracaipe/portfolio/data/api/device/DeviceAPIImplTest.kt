package br.com.raphaelmaracaipe.portfolio.data.api.device

import android.content.Context
import android.os.Build
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.const.ConfigsToTest
import br.com.raphaelmaracaipe.portfolio.data.api.enums.CodeError
import br.com.raphaelmaracaipe.portfolio.data.api.models.HttpError
import br.com.raphaelmaracaipe.portfolio.data.api.models.RequestDeviceInfo
import br.com.raphaelmaracaipe.portfolio.data.api.retrofit.ConfigurationServiceImpl
import br.com.raphaelmaracaipe.portfolio.data.sp.device.DeviceSP
import br.com.raphaelmaracaipe.portfolio.data.sp.device.DeviceSPImpl
import br.com.raphaelmaracaipe.portfolio.data.sp.token.TokenSP
import br.com.raphaelmaracaipe.portfolio.data.sp.token.TokenSPImpl
import br.com.raphaelmaracaipe.portfolio.utils.device.DeviceNetworkImpl
import br.com.raphaelmaracaipe.portfolio.utils.security.encryptDecrypt.EncryptDecryptImpl
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.M])
@RunWith(RobolectricTestRunner::class)
class DeviceAPITest {

    private val mockWebServer = MockWebServer()
    private lateinit var deviceAPI: DeviceAPI
    private lateinit var context: Context
    private var deviceNetwork: DeviceNetworkImpl = mockk()

    @Before
    fun setUp() {
        mockWebServer.start()
        ConfigsToTest.urlToMock = mockWebServer.url("").toString()

        context = RuntimeEnvironment.getApplication().applicationContext
        val deviceSP: DeviceSP = DeviceSPImpl(context, EncryptDecryptImpl())
        val tokenSP: TokenSP = TokenSPImpl(context, EncryptDecryptImpl())

        deviceAPI = DeviceAPIImpl(
            context,
            ConfigurationServiceImpl(
                context,
                deviceNetwork,
                EncryptDecryptImpl(),
                deviceSP,
                tokenSP,
                R.string.err_not_connection_internet
            ),
        )
    }

    @Test
    fun `when want send information about messagem`() = runBlocking {
        every { deviceNetwork.isNetworkConnected() } answers { false }
        try {
            val returnApi = deviceAPI.sendInformationAboutDevice(RequestDeviceInfo())
            Assert.assertTrue(returnApi)
        } catch (e: Exception) {
            Assert.assertEquals(getRes(R.string.err_not_connection_internet), e.message)
        }
    }

    @Test
    fun `when want send information about device but api return error`() = runBlocking {
        every { deviceNetwork.isNetworkConnected() } answers { true }

        val httpError = HttpError(message = "", code = CodeError.GENERAL_ERROR.code)
        with(mockWebServer) {
            enqueue(
                MockResponse()
                    .setResponseCode(401)
                    .setBody(httpError.toJSON())
            )
        }

        try {
            val returnApi = deviceAPI.sendInformationAboutDevice(RequestDeviceInfo())
            Assert.assertTrue(returnApi)
        } catch (e: Exception) {
            Assert.assertEquals(getRes(R.string.err_general_server), e.message)
        }
    }

    @Test
    fun `when want send information about device and api return success`() = runBlocking {
        every { deviceNetwork.isNetworkConnected() } answers { true }

        val requestDeviceInfo = RequestDeviceInfo(keyToCommunication = "aaa")
        with(mockWebServer) {
            enqueue(
                MockResponse()
                    .setResponseCode(201)
                    .setBody(requestDeviceInfo.toJSON())
            )
        }

        val returnApi = deviceAPI.sendInformationAboutDevice(RequestDeviceInfo())
        Assert.assertTrue(returnApi)
    }

    private fun getRes(res: Int): String = context.resources.getString(res)

}