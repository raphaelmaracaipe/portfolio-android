package br.com.raphaelmaracaipe.core.socket

object Socket {

    fun getInstance() : ISocket = SocketImpl()

}