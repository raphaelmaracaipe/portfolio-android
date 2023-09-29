package br.com.raphaelmaracaipe.core.extensions

import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test

class ByteExtensionsTest {

    @Test
    fun `when you want convert byte to array`() {
        val textOnByte: Array<Byte> = arrayOf(116, 101, 115, 116)
        val textOnString = textOnByte.convertToString()
        assertEquals("test", textOnString)
    }

}