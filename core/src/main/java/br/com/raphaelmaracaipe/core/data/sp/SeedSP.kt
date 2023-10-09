package br.com.raphaelmaracaipe.core.data.sp

interface SeedSP {
    fun get(): String
    fun save(seed: String)
    fun clean()
}