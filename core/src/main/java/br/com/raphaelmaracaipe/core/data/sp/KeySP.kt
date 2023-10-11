package br.com.raphaelmaracaipe.core.data.sp

interface KeySP {

    fun get(): String

    fun save(key: String)

    fun clean()
}