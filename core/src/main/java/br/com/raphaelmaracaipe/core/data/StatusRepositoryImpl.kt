package br.com.raphaelmaracaipe.core.data

import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException
import br.com.raphaelmaracaipe.core.socket.ISocket
import io.socket.client.Socket
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StatusRepositoryImpl(
    private val socket: ISocket
) : StatusRepository {

    private val socketInstance: Socket by lazy {
        socket.socket(
            "contact-status"
        ) ?: throw NetworkException(NetworkCodeEnum.ERROR_SOCKET.code)
    }

    override suspend fun connect() {
        val complete = CompletableDeferred<Unit>()
        with(socketInstance) {
            connect()
            on("connect") {
                complete.complete(Unit)
            }
        }

        return withContext(Dispatchers.IO) {
            complete.await()
        }
    }

    override fun checkStatus(phone: String) {
        socketInstance.emit("checkStatus", phone)
    }

    override fun onIAmOnline(myPhone: String) {
        socketInstance.on("status$myPhone") {
            socketInstance.emit("iAmOnline", myPhone)
        }
    }

    override fun onIsHeOnline(myPhone: String, onResponse: () -> Unit) {
        socketInstance.on("isHeOnline$myPhone") {
            onResponse.invoke()
        }
    }
}