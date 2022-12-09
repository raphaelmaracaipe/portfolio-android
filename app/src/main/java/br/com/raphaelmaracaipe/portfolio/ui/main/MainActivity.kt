package br.com.raphaelmaracaipe.portfolio.ui.main

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import br.com.raphaelmaracaipe.portfolio.App
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.const.EVENT_KEY_LOADING
import br.com.raphaelmaracaipe.portfolio.databinding.ActivityMainBinding
import br.com.raphaelmaracaipe.portfolio.ui.userUpdatePassword.UpdatePasswordBottomSheet
import br.com.raphaelmaracaipe.portfolio.utils.events.Event
import br.com.raphaelmaracaipe.portfolio.utils.events.EventModule
import com.google.android.material.snackbar.Snackbar
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
        settingActionBar()
        callbackOfEventLoading()
        event.registerEvent(EVENT_KEY_LOADING)
    }

    override fun onStart() {
        super.onStart()
        getInformationDeepLink()
    }

    private fun getInformationDeepLink() {
        intent.data.apply {
            val urlSplitted = toString().split("=")
            if(urlSplitted.size == 2) {
                val code = urlSplitted[1]
                showBottomSheetToUpdatePassword(code)
            }
        }
    }

    private fun showBottomSheetToUpdatePassword(code: String) {
        UpdatePasswordBottomSheet.showAlertToUpdatePassword(
            supportFragmentManager,
            code
        ) { msgRes ->
            Snackbar.make(bind.root, msgRes, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun settingActionBar() {
        setSupportActionBar(bind.toolbar)
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

    override fun onDestroy() {
        super.onDestroy()
        event.unregisterEvent()
    }

    fun closeKeyboard() {
        this.currentFocus?.let { view ->
            val inputMethod = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethod?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}