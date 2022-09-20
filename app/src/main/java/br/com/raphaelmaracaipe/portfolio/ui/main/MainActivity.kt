package br.com.raphaelmaracaipe.portfolio.ui.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.const.BROADCAST_KEY_LOADING
import br.com.raphaelmaracaipe.portfolio.const.BROADCAST_LOADING_IS_SHOW
import br.com.raphaelmaracaipe.portfolio.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bind: ActivityMainBinding

    private val loadingBroadcastReceiver by lazy {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val isShow = intent?.extras?.getBoolean(
                    BROADCAST_LOADING_IS_SHOW
                ) ?: false

                bind.showLoading = isShow
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingBindOfView()
    }

    private fun settingBindOfView() {
        bind = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(bind.root)
    }

    override fun onStart() {
        super.onStart()
        registerBroadcast()
    }

    override fun onStop() {
        super.onStop()
        unregisterBroadcast()
    }

    private fun registerBroadcast() {
        registerReceiver(loadingBroadcastReceiver, IntentFilter(BROADCAST_KEY_LOADING))
    }

    private fun unregisterBroadcast() {
        unregisterReceiver(loadingBroadcastReceiver)
    }

    companion object {
        fun showLoading(context: Context) {
            val intent = Intent(BROADCAST_KEY_LOADING)
            intent.putExtra(BROADCAST_LOADING_IS_SHOW, true)
            context.sendBroadcast(intent)
        }

        fun hiddenLoading(context: Context) {
            val intent = Intent(BROADCAST_KEY_LOADING)
            intent.putExtra(BROADCAST_LOADING_IS_SHOW, false)
            context.sendBroadcast(intent)
        }
    }

}