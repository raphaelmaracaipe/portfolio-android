package br.com.raphaelmaracaipe.core.data.sp

import android.content.Context
import androidx.core.content.edit
import br.com.raphaelmaracaipe.core.extensions.toSha1
import br.com.raphaelmaracaipe.core.externals.KeysDefault
import br.com.raphaelmaracaipe.core.externals.SpKeyDefault
import br.com.raphaelmaracaipe.core.security.CryptoHelper
import javax.inject.Inject

class AuthSPImpl @Inject constructor(
    private val context: Context,
    private val keysDefault: KeysDefault,
    private val spKeyDefault: SpKeyDefault,
    private val cryptoHelper: CryptoHelper
) : AuthSP {

    private val sp by lazy {
        val key = cryptoHelper.encrypt(
            spKeyDefault.authSpKey,
            keysDefault.key,
            keysDefault.seed
        ).toSha1()

        context.getSharedPreferences(key, Context.MODE_PRIVATE)
    }

    private val keyPhoneEdit by lazy {
        cryptoHelper.encrypt(
            spKeyDefault.authPhoneKey,
            keysDefault.key,
            keysDefault.seed
        ).toSha1()
    }

    override fun setPhone(phone: String) {
        sp.edit {
            putString(keyPhoneEdit, phone)
            commit()
        }
    }

    override fun getPhone() = sp.getString(keyPhoneEdit, "") ?: ""

}