package br.com.raphaelmaracaipe.portfolio.utils.regex

import org.junit.Assert
import org.junit.Test

class RegexGenerateTest {

    @Test
    fun `when you want generate code and check if is valid`() {
        val regexGenerate: RegexGenerate = RegexGenerateImpl()
        val patternCode = "[0-9]{10}"
        val code = regexGenerate.generateRandom(patternCode)
        Assert.assertTrue(regexGenerate.containsMatchIn(patternCode, code))
    }

}