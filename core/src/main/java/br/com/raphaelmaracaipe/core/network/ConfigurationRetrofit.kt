package br.com.raphaelmaracaipe.core.network

interface ConfigurationRetrofit {

    fun <T : Any> create(service: Class<T>): T

}