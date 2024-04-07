package br.com.raphaelmaracaipe.core.extensions

import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import java.nio.charset.Charset

class ByteExtensionsTest {

    @Test
    fun `when get byte array and convert to text`() {
        val textTest = "test"
        val textInArrayByte = textTest.toByteArray(Charset.defaultCharset())
        val arrayByteToList = textInArrayByte.toList()
        val byteListToArrayByte = arrayByteToList.toTypedArray()
        val arrayByteToText = byteListToArrayByte.convertToString()
        assertEquals(textTest, arrayByteToText)
    }

}