package br.com.raphaelmaracaipe.portfolio.utils.events

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import br.com.raphaelmaracaipe.portfolio.utils.events.model.EventModel
import com.google.gson.Gson

class EventImpl(
    private val context: Context
): Event {

    private lateinit var loadingBroadcastReceiver: BroadcastReceiver

    override fun <T> send(key: String, value: T) {
        val eventModel = EventModel(value)
        val convertToGson = Gson().toJson(eventModel)

        val intent = Intent(key).apply {
            putExtra(key, convertToGson)
        }

        context.sendBroadcast(intent)
    }

    override fun <T> callback(key: String, callback: ((value: T) -> Unit)) {
        loadingBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val valueOfIntent = intent?.extras?.getString(key) ?: ""
                if(valueOfIntent.isNotEmpty()) {
                    val eventModel = Gson().fromJson(valueOfIntent, EventModel::class.java)
                    callback(eventModel.data as T)
                } else {
                    throw Exception("The key informed don't have value, please check your key!")
                }
            }
        }
    }

    override fun registerEvent(key: String) {
        context.registerReceiver(
            loadingBroadcastReceiver, IntentFilter(key)
        )
    }

    override fun unregisterEvent() {
        context.unregisterReceiver(
            loadingBroadcastReceiver
        )
    }

}