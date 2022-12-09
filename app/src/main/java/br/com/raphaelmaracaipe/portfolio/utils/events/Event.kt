package br.com.raphaelmaracaipe.portfolio.utils.events

import android.content.Intent
import android.os.Bundle

interface Event {
    fun <T> send(key: String, value: T)
    fun <T> callback(key: String, callback: (value: T) -> Unit)
    fun registerEvent(key: String)
    fun unregisterEvent()
}