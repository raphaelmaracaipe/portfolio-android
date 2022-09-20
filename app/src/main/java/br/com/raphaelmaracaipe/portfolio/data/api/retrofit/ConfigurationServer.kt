package br.com.raphaelmaracaipe.portfolio.data.api.retrofit

interface ConfigurationServer {

    fun <T : Any> create(service: Class<T>): T

}