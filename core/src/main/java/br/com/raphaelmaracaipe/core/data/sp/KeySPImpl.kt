package br.com.raphaelmaracaipe.core.data.sp

import android.content.Context
import androidx.core.content.edit
import br.com.raphaelmaracaipe.core.network.utils.KeysDefault
import br.com.raphaelmaracaipe.core.security.CryptoHelper

class KeySPImpl(
    private val context: Context,
    private val keysDefault: KeysDefault,
    private val cryptoHelper: CryptoHelper
) : KeySP {

    private val sharedPreferences = context.getSharedPreferences("keys", Context.MODE_PRIVATE)
    private val KEY = "keyEncrypted"

    override fun get(): String {
        var keySaved = sharedPreferences.getString(KEY, "") ?: ""
        if (keySaved.isNotEmpty()) {
            keySaved = cryptoHelper.decrypt(keySaved, keysDefault.key, keysDefault.seed)
        }
        return keySaved
    }

    override fun save(key: String) {
        sharedPreferences.edit {
            putString(KEY, cryptoHelper.encrypt(key, keysDefault.key, keysDefault.seed))
            commit()
        }
    }

}