package br.com.raphaelmaracaipe.portfolio.utils.regex

interface RegexGenerate {

    fun generateRandom(pattern: String): String

    fun containsMatchIn(pattern: String, textWillBeChecked: String): Boolean

}