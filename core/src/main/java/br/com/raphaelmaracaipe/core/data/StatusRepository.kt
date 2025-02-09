package br.com.raphaelmaracaipe.core.data

interface StatusRepository {
    suspend fun connect()
    fun checkStatus(phone: String)
    fun onIAmOnline(myPhone: String)
    fun onIsHeOnline(myPhone: String, onResponse: () -> Unit)
}