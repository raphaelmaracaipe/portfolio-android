package br.com.raphaelmaracaipe.portfolio.data.sp.token

import android.content.Context
import androidx.core.content.edit
import br.com.raphaelmaracaipe.portfolio.BuildConfig
import br.com.raphaelmaracaipe.portfolio.models.TokenModel
import br.com.raphaelmaracaipe.portfolio.utils.security.encryptDecrypt.EncryptDecrypt

class TokenSPImpl(
    private val context: Context,
    private val encryptDecrypt: EncryptDecrypt
) : TokenSP {

    private val KEY = "token_key"

    override fun save(data: TokenModel) {
        val textEncryptDecrypt = this.encryptDecrypt.encryptMessage(data.toJSON(), BuildConfig.KEY)
        val sharedPreference = context.getSharedPreferences(KEY, Context.MODE_PRIVATE)
        with(sharedPreference.edit()) {
            putString(KEY, textEncryptDecrypt)
            apply()
        }
    }

    override fun exist(): Boolean {
        val sharedPreferences = context.getSharedPreferences(KEY, Context.MODE_PRIVATE)
        val token = sharedPreferences.getString(KEY, "") ?: ""
        return token.isNotEmpty()
    }

    override fun clearAll() {
        context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit {
            remove(KEY)
        }
    }

}