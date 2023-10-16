package br.com.raphaelmaracaipe.core.data

interface KeyRepository {
    fun get(): String
    suspend fun saveKeyGenerated(key: String)
    suspend fun isExistKeyRegistered(): Boolean
}