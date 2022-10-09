package br.com.raphaelmaracaipe.portfolio.data.sp.userPassword

import android.content.Context
import javax.inject.Inject

class UserPasswordImpl @Inject constructor(
    private val context: Context
) : UserPassword {

    private val USER_REGISTER_PASSWORD_KEY = "USER_REGISTER_PASSWORD_KEY"
    private val USER_REGISTER_PASSWORD = "USER_REGISTER_PASSWORD"

    override fun save(text: String) {
        context.getSharedPreferences(USER_REGISTER_PASSWORD_KEY, Context.MODE_PRIVATE).apply {
            edit().putString(USER_REGISTER_PASSWORD, text).apply()
        }
    }

    override fun get(): String {
        val sp = context.getSharedPreferences(USER_REGISTER_PASSWORD_KEY, Context.MODE_PRIVATE)
        return sp.getString(USER_REGISTER_PASSWORD, "") ?: ""
    }


}