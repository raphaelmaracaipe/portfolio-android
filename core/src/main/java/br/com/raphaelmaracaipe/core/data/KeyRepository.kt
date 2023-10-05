package br.com.raphaelmaracaipe.core.data

interface KeyRepository {
    fun get(): String
    fun saveKeyGenerated(key: String)
    fun isExistKeyRegistered(): Boolean
}