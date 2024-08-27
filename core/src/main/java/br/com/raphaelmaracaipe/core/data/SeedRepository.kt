package br.com.raphaelmaracaipe.core.data

interface SeedRepository {
    fun cleanSeedSaved()
    fun get(): String
}