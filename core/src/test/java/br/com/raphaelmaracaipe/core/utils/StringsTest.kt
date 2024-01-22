package br.com.raphaelmaracaipe.core.utils

import org.junit.Assert.*
import org.junit.Test

class StringsTest {

    @Test
    fun `when generated string random with size should return string with size equal`() {
        val stringGenerated = Strings.generateStringRandom(8)
        assertEquals(8, stringGenerated.length)
    }

}