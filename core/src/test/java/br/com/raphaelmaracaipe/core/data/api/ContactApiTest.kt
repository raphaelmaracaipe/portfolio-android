package br.com.raphaelmaracaipe.core.data.api

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.configRetrofitTest
import br.com.raphaelmaracaipe.core.data.api.response.ContactResponse
import br.com.raphaelmaracaipe.core.data.api.services.ContactService
import br.com.raphaelmaracaipe.core.externals.KeysDefault
import br.com.raphaelmaracaipe.core.externals.NetworkUtils
import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException
import br.com.raphaelmaracaipe.core.test.setBodyEncrypted
import com.google.gson.Gson
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
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class ContactApiTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val mockWebServer = MockWebServer()
    private lateinit var contactService: ContactService
    private lateinit var contactApi: ContactApi

    @Before
    fun setUp() {
        mockWebServer.start()
        NetworkUtils.URL_TO_MOCK = mockWebServer.url("").toString()

        val keysDefault = KeysDefault("nDHj82ZWov6r4bnu", "30rBgU6kuVSHPNXX")
        contactService = configRetrofitTest(
            ContactService::class.java,
            keysDefault
        )

        contactApi = ContactApiImpl(contactService)
    }

    @Test
    fun `when call api send lot with contacts but return success`() = runBlocking {
        val contactJSON = Gson().toJson(
            arrayListOf(
                ContactResponse(
                    name = "test",
                    phone = "555",
                    photo = "=AA"
                )
            )
        )

        mockWebServer.enqueue(
            MockResponse().setResponseCode(201).setBodyEncrypted(contactJSON)
        )

        try {
            val returnApi = contactApi.consult(arrayListOf("555", "666"))
            assertEquals(1, returnApi.size)
        } catch (_: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when call api send lot with contacts but return fail`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(500).setBody("{}")
        )

        try {
            contactApi.consult(arrayListOf("555", "666"))
            assertTrue(false)
        } catch (e: NetworkException) {
            assertEquals(NetworkCodeEnum.ERROR_GENERAL.code, e.code)
        } catch (_: Exception) {
            assertTrue(true)
        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

}