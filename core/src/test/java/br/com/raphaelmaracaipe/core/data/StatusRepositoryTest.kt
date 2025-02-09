package br.com.raphaelmaracaipe.core.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.socket.ISocket
import io.mockk.every
import io.mockk.mockk
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class StatusRepositoryTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private lateinit var statusRepository: StatusRepositoryImpl
    private lateinit var mockSocket: ISocket
    private lateinit var mockSocketInstance: Socket

    @Before
    fun setUp() {
        mockSocket = mockk(relaxed = true)
        mockSocketInstance = mockk(relaxed = true)
    }

    @Test
    fun `connect should throw NetworkException when socket fails`() = runBlocking {
        every { mockSocket.socket("contact-status") } throws Exception()
        try {
            statusRepository = StatusRepositoryImpl(mockSocket)
            statusRepository.connect()
            fail()
        } catch (e: Exception) {
            assertTrue(true)
        }
    }

    @Test
    fun `connect should complete when socket connects successfully`() = runBlocking {
        every { mockSocket.socket("contact-status") } returns mockSocketInstance
        every { mockSocketInstance.connect() } returns mockk()
        every { mockSocketInstance.on("connect", any()) } answers {
            secondArg<Emitter.Listener>().call("test")
            mockSocketInstance
        }

        try {
            statusRepository = StatusRepositoryImpl(mockSocket)
            statusRepository.connect()
            assertTrue(true)
        } catch (e: Exception) {
            assertTrue(true)
        }
    }

    @Test
    fun `when check status `() {
        every { mockSocket.socket("contact-status") } returns mockSocketInstance
        every { mockSocketInstance.emit(any(), any()) } returns mockk()

        try {
            statusRepository = StatusRepositoryImpl(mockSocket)
            statusRepository.checkStatus("123456789")
            assertTrue(true)
        } catch (e: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when check status`() {
        every { mockSocket.socket("contact-status") } returns mockSocketInstance
        every { mockSocketInstance.connect() } returns mockk()
        every { mockSocketInstance.on(any(), any()) } answers {
            secondArg<Emitter.Listener>().call("test")
            mockSocketInstance
        }

        statusRepository = StatusRepositoryImpl(mockSocket)
        statusRepository.onIAmOnline("aaaa")
    }

    @Test
    fun `when consult of is he online return with success`() {
        every { mockSocket.socket("contact-status") } returns mockSocketInstance
        every { mockSocketInstance.on(any(), any()) } answers {
            secondArg<Emitter.Listener>().call("test")
            mockSocketInstance
        }

        statusRepository = StatusRepositoryImpl(mockSocket)
        statusRepository.onIsHeOnline("test") {
            assertTrue(true)
        }
    }

}
