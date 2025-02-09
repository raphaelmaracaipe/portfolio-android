package br.com.raphaelmaracaipe.core.socket

import br.com.raphaelmaracaipe.core.BuildConfig.URL_SOCKET
import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException
import io.socket.client.IO
import io.socket.client.Socket

internal class SocketImpl : ISocket {

    override fun socket(path: String): Socket? = try {
        IO.socket("${URL_SOCKET}/$path")
    } catch (e: Exception) {
        null
    }

}