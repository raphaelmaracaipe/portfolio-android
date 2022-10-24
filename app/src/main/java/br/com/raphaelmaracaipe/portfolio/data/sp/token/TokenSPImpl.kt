package br.com.raphaelmaracaipe.portfolio.data.sp.token

import android.content.Context
import androidx.core.content.edit
import br.com.raphaelmaracaipe.portfolio.models.TokenModel

class TokenSPImpl(
    private val context: Context
): TokenSP {

    private val KEY = "token_key"

    override fun save(data: TokenModel) {
        val sharedPreference = context.getSharedPreferences(KEY, Context.MODE_PRIVATE)
        with(sharedPreference.edit()) {
            putString(KEY, data.toJSON())
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