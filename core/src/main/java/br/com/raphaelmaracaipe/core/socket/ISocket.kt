package br.com.raphaelmaracaipe.core.socket

import io.socket.client.Socket

interface ISocket {
    fun socket(path: String): Socket?
}