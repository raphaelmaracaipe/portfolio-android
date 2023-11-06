package br.com.raphaelmaracaipe.core.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.data.api.HandShakeApi
import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class HandShakeRepositoryTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @Test
    fun `when send to key and return success`() = runBlocking {
        val handShakeApi = mockk<HandShakeApi>()
        coEvery { handShakeApi.send() } returns "AAA"

        val handShakeRepository: HandShakeRepository = HandShakeRepositoryImpl(handShakeApi)
        val returnAfterSender = handShakeRepository.send()

        assertEquals("AAA", returnAfterSender)
    }

    @Test
    fun `when send to key but return error`() = runBlocking {
        val handShakeApi = mockk<HandShakeApi>()
        coEvery { handShakeApi.send() } throws NetworkException(NetworkCodeEnum.SEED_INVALID.code)

        val handShakeRepository: HandShakeRepository = HandShakeRepositoryImpl(handShakeApi)
        try {
            handShakeRepository.send()
            assertTrue(false)
        } catch (e: NetworkException) {
            assertEquals(4001, e.code)
        }
    }

    @Test
    fun `when send to key but return error generic`() = runBlocking {
        val handShakeApi = mockk<HandShakeApi>()
        coEvery { handShakeApi.send() } throws Exception("error")

        val handShakeRepository: HandShakeRepository = HandShakeRepositoryImpl(handShakeApi)
        try {
            handShakeRepository.send()
            assertTrue(false)
        } catch (e: NetworkException) {
            assertEquals(NetworkCodeEnum.ERROR_GENERAL.code, e.code)
        }
    }

}