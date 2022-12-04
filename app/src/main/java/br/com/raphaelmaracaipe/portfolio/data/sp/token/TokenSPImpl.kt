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
    private val KEY_TOKENS =  "KEY_TOKENS"
    private val KEY_COMMUNICATION = "KEY_COMMUNICATION"

    override fun save(data: TokenModel) {
        val textEncryptDecrypt = this.encryptDecrypt.encryptMessage(data.toJSON(), BuildConfig.KEY)
        val sharedPreference = context.getSharedPreferences(KEY, Context.MODE_PRIVATE)
        with(sharedPreference.edit()) {
            putString(KEY_TOKENS, textEncryptDecrypt)
            apply()
        }
    }

    override fun exist(): Boolean {
        val sharedPreferences = context.getSharedPreferences(KEY, Context.MODE_PRIVATE)
        val token = sharedPreferences.getString(KEY_TOKENS, "") ?: ""
        return token.isNotEmpty()
    }

    override fun savedKeyOfCommunications(key: String) {
        val keyEncrypted = this.encryptDecrypt.encryptMessage(key, BuildConfig.KEY)
        context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit {
            putString(KEY_COMMUNICATION, keyEncrypted)
        }
    }

    override fun getKeyOfCommunication(): String {
        val keySaved = context.getSharedPreferences(
            KEY,
            Context.MODE_PRIVATE
        ).getString(KEY_COMMUNICATION, "") ?: ""

        return if(keySaved.isNotEmpty()) {
            this.encryptDecrypt.decryptMessage(keySaved, BuildConfig.KEY)
        } else {
            BuildConfig.KEY
        }
    }

    override fun clearAll() {
        context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit {
            remove(KEY_TOKENS)
            remove(KEY_COMMUNICATION)
        }
    }

}