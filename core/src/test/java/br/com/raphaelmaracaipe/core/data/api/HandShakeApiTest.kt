package br.com.raphaelmaracaipe.core.data.api

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.configRetrofitTest
import br.com.raphaelmaracaipe.core.data.api.services.HandShakeService
import br.com.raphaelmaracaipe.core.externals.NetworkUtils
import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException
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
class HandShakeApiTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val mockWebServer = MockWebServer()
    private lateinit var handShakeService: HandShakeService
    private lateinit var handShakeApi: HandShakeApi

    @Before
    fun setUp() {
        mockWebServer.start()
        NetworkUtils.URL_TO_MOCK = mockWebServer.url("").toString()

        handShakeService = configRetrofitTest(
            HandShakeService::class.java
        )

        handShakeApi = HandShakeApiImpl(handShakeService)
    }


    @Test
    fun `when call api to send code and return success`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(201).setBody("{}")
        )

        try {
            val returnAfterCallApi = handShakeApi.send()
            assertNotEquals("", returnAfterCallApi)
        } catch (_: Exception) {
            assertTrue(false)
        }
    }


    @Test
    fun `when call api to send code and return fail`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(500).setBody("{}")
        )

        try {
            val returnAfterCallApi = handShakeApi.send()
            assertNotEquals("", returnAfterCallApi)
        } catch (e: NetworkException) {
            assertEquals(NetworkCodeEnum.ERROR_GENERAL.code, e.code)
        } catch (_: Exception) {
            assertTrue(false)
        }
    }

    @After
    fun after() {
        mockWebServer.shutdown()
    }

}