package br.com.raphaelmaracaipe.core.network

interface Network {

    fun <T : Any> create(service: Class<T>): T

}