package br.com.raphaelmaracaipe.portfolio.ui.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import br.com.raphaelmaracaipe.portfolio.App
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.const.EVENT_KEY_LOADING
import br.com.raphaelmaracaipe.portfolio.databinding.ActivityMainBinding
import br.com.raphaelmaracaipe.portfolio.utils.events.Event
import br.com.raphaelmaracaipe.portfolio.utils.events.EventModule
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var bind: ActivityMainBinding

    @Inject
    @EventModule.Events
    lateinit var event: Event

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prepareInject()
        settingBindOfView()
        callbackOfEventLoading()
    }

    private fun callbackOfEventLoading() {
        event.callback<Boolean>(key = EVENT_KEY_LOADING) { isShow ->
            bind.showLoading = isShow
        }
    }

    private fun prepareInject() {
        (applicationContext as App).appComponent.mainSubcomponent().create().inject(this)
    }

    private fun settingBindOfView() {
        bind = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(bind.root)
    }

    override fun onStart() {
        super.onStart()
        event.registerEvent(EVENT_KEY_LOADING)
    }

    override fun onStop() {
        super.onStop()
        event.unregisterEvent()
    }

}