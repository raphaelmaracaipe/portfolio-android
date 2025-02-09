package br.com.raphaelmaracaipe.core.socket

import org.junit.Assert.*
import org.junit.Test

class SocketTest {

    @Test
    fun `when instante socket`() {
        val socketInstance = Socket.getInstance().socket("test")
        assertNotNull(socketInstance)
    }

}