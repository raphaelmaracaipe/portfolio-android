package br.com.raphaelmaracaipe.core.data

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.data.api.HandShakeApi
import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum.DEVICE_ID_INVALID
import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum.ERROR_GENERAL
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
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
class HandShakeRepositoryTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private lateinit var handShakeApi: HandShakeApi
    private lateinit var handShakeRepository: HandShakeRepository

    @Before
    fun setUp() {
        handShakeApi = mockk()
        handShakeRepository = HandShakeRepositoryImpl(handShakeApi)
    }

    @Test
    fun `when send data and return success`() = runBlocking {
        coEvery { handShakeApi.send() } returns "test"

        try {
            val dataReturned = handShakeRepository.send()
            assertEquals("test", dataReturned)
        } catch (_: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when send data but return network exception`() = runBlocking {
        coEvery { handShakeApi.send() } throws NetworkException(DEVICE_ID_INVALID.code)

        try {
            handShakeRepository.send()
            assertTrue(false)
        } catch (e: NetworkException) {
            assertEquals(DEVICE_ID_INVALID.code, e.code)
        } catch (_: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when send data but return exception`() = runBlocking {
        coEvery { handShakeApi.send() } throws Exception("fail")

        try {
            handShakeRepository.send()
            assertTrue(false)
        } catch (e: NetworkException) {
            assertEquals(ERROR_GENERAL.code, e.code)
        } catch (_: Exception) {
            assertTrue(false)
        }
    }

}