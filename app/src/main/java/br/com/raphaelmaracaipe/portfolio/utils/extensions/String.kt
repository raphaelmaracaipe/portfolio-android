package br.com.raphaelmaracaipe.portfolio.utils.extensions

import java.text.Normalizer

fun String.removeNonSpacingMarks() = Normalizer.normalize(
    this,
    Normalizer.Form.NFD
).replace("\\p{Mn}+".toRegex(), "")