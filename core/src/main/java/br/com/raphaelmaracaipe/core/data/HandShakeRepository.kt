package br.com.raphaelmaracaipe.core.data

interface HandShakeRepository {
    suspend fun send(): String
}