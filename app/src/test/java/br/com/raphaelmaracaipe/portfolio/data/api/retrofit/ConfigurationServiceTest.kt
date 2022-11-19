package br.com.raphaelmaracaipe.portfolio.data.api.retrofit

import android.os.Build
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.const.ConfigsToTest
import br.com.raphaelmaracaipe.portfolio.data.api.user.UserAPI
import br.com.raphaelmaracaipe.portfolio.data.api.user.UserAPIImpl
import br.com.raphaelmaracaipe.portfolio.data.sp.device.DeviceSP
import br.com.raphaelmaracaipe.portfolio.data.sp.device.DeviceSPImpl
import br.com.raphaelmaracaipe.portfolio.utils.device.DeviceNetwork
import br.com.raphaelmaracaipe.portfolio.utils.device.DeviceNetworkImpl
import br.com.raphaelmaracaipe.portfolio.utils.regex.RegexGenerate
import br.com.raphaelmaracaipe.portfolio.utils.regex.RegexGenerateImpl
import br.com.raphaelmaracaipe.portfolio.utils.security.encryptDecrypt.EncryptDecryptImpl
import kotlinx.coroutines.runBlocking
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.json.JSONObject
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.robolectric.RuntimeEnvironment

@Config(sdk = [Build.VERSION_CODES.M])
@RunWith(RobolectricTestRunner::class)
class ConfigurationServiceTest {

    private val mockWebServer = MockWebServer()
    private lateinit var userAPI: UserAPI

    @Before
    fun setUp() {
        mockWebServer.start()
        ConfigsToTest.urlToMock = mockWebServer.url("").toString()

        val context = RuntimeEnvironment.getApplication().applicationContext
        val deviceNetwork: DeviceNetwork = DeviceNetworkImpl(context)
        val deviceSP: DeviceSP = DeviceSPImpl(context, EncryptDecryptImpl())
        val regexGenerate: RegexGenerate = RegexGenerateImpl()

        userAPI = UserAPIImpl(
            context,
            ConfigurationServiceImpl(
                context,
                deviceNetwork,
                R.string.err_not_connection_internet
            ),
            deviceSP,
            regexGenerate
        )
    }

    @Test
    fun `when call api to request information should return information`() = runBlocking {
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

}