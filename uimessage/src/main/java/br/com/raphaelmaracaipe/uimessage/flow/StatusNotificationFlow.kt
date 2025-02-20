package br.com.raphaelmaracaipe.uimessage.flow

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

internal object StatusNotificationFlow {

    private val _statusFlow = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val statusFlow = _statusFlow.asSharedFlow()

    fun notifyStatus() {
        _statusFlow.tryEmit(Unit)
    }
}