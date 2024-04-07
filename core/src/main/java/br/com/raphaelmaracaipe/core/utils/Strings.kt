package br.com.raphaelmaracaipe.core.utils

import java.util.Random

object Strings {

    fun generateStringRandom(size: Int): String {
        val caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        val sb = StringBuilder(size)

        val random = Random()
        for (i in 0 until size) {
            val index = random.nextInt(caracteres.length)
            sb.append(caracteres[index])
        }

        return sb.toString()
    }

}