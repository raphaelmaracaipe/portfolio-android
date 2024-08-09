package br.com.raphaelmaracaipe.uiauth.data.sp

import android.content.Context
import javax.inject.Inject

class AuthSPImpl @Inject constructor(
    private val context: Context
): AuthSP {

    private val AUTH_KEY = "authKey"
    private val PHONE = "phone"

    override fun setPhone(phone: String) {
        context.getSharedPreferences(AUTH_KEY, Context.MODE_PRIVATE).edit().apply {
            putString(PHONE, phone)
            apply()
        }
    }

    override fun getPhone() = context.getSharedPreferences(
        AUTH_KEY,
        Context.MODE_PRIVATE
    ).getString(PHONE, "") ?: ""

}