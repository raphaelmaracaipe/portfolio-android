package br.com.raphaelmaracaipe.core.data.sp

import android.content.Context
import androidx.core.content.edit
import br.com.raphaelmaracaipe.core.extensions.toSha1
import br.com.raphaelmaracaipe.core.externals.KeysDefault
import br.com.raphaelmaracaipe.core.externals.SpKeyDefault
import br.com.raphaelmaracaipe.core.security.CryptoHelper

class KeySPImpl(
    private val context: Context,
    private val keysDefault: KeysDefault,
    private val spKeyDefault: SpKeyDefault,
    private val cryptoHelper: CryptoHelper
) : KeySP {

    private val sharedPreferences by lazy {
        val key = cryptoHelper.encrypt(
            spKeyDefault.keySp,
            keysDefault.key,
            keysDefault.seed
        ).toSha1()

        context.getSharedPreferences(key, Context.MODE_PRIVATE)
    }

    private val keyEdit by lazy {
        cryptoHelper.encrypt(
            spKeyDefault.keySpEdit,
            keysDefault.key,
            keysDefault.seed
        )
    }

    override fun get(): String {
        val keySaved = sharedPreferences.getString(keyEdit, "") ?: ""
        if(keySaved.isEmpty()) {
            return keySaved
        }

        return cryptoHelper.decrypt(keySaved, keysDefault.key, keysDefault.seed)
    }

    override fun save(key: String) {
        val keyEncrypted = cryptoHelper.encrypt(key, keysDefault.key, keysDefault.seed)
        sharedPreferences.edit {
            putString(keyEdit, keyEncrypted)
            commit()
        }
    }

    override fun clean() {
        sharedPreferences.edit {
            clear()
            commit()
        }
    }

}