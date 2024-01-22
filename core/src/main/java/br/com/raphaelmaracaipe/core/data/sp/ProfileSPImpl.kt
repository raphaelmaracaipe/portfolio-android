package br.com.raphaelmaracaipe.core.data.sp

import android.content.Context
import androidx.core.content.edit
import br.com.raphaelmaracaipe.core.extensions.toSha1
import br.com.raphaelmaracaipe.core.externals.KeysDefault
import br.com.raphaelmaracaipe.core.externals.SpKeyDefault
import br.com.raphaelmaracaipe.core.security.CryptoHelper

class ProfileSPImpl(
    private val context: Context,
    private val keysDefault: KeysDefault,
    private val spKeyDefault: SpKeyDefault,
    private val cryptoHelper: CryptoHelper
): ProfileSP {

    private val sp by lazy {
        val key = cryptoHelper.encrypt(
            spKeyDefault.profileSp,
            keysDefault.key,
            keysDefault.seed
        ).toSha1()

        context.getSharedPreferences(key, Context.MODE_PRIVATE)
    }

    private val keyEdit by lazy {
        cryptoHelper.encrypt(
            spKeyDefault.profileSpEdit,
            keysDefault.key,
            keysDefault.seed
        ).toSha1()
    }

    override fun markWithExistProfile() {
        sp.edit {
            putBoolean(keyEdit, true)
            commit()
        }
    }

    override fun isExistProfileSaved(): Boolean = sp.getBoolean(keyEdit, false)

}