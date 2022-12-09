package br.com.raphaelmaracaipe.portfolio.utils.regex

import com.mifmif.common.regex.Generex

class RegexGenerateImpl: RegexGenerate {

    override fun generateRandom(pattern: String): String = Generex(pattern).random()

    override fun containsMatchIn(pattern: String, textWillBeChecked: String): Boolean {
        val regex = Regex(pattern)
        return regex.containsMatchIn(textWillBeChecked)
    }

}