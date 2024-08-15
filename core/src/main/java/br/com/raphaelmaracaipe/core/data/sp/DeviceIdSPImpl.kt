package br.com.raphaelmaracaipe.core.data.sp

import android.content.Context
import androidx.core.content.edit
import br.com.raphaelmaracaipe.core.extensions.toSha1
import br.com.raphaelmaracaipe.core.externals.KeysDefault
import br.com.raphaelmaracaipe.core.externals.SpKeyDefault
import br.com.raphaelmaracaipe.core.security.CryptoHelper
import javax.inject.Inject

class DeviceIdSPImpl @Inject constructor(
    private val context: Context,
    private val keysDefault: KeysDefault,
    private val spKeyDefault: SpKeyDefault,
    private val cryptoHelper: CryptoHelper
) : DeviceIdSP {

    private val sharedPreferences by lazy {
        val key = cryptoHelper.encrypt(
            spKeyDefault.deviceIdKey,
            keysDefault.key,
            keysDefault.seed
        ).toSha1()

        context.getSharedPreferences(key, Context.MODE_PRIVATE)
    }

    private val keyEdit by lazy {
        cryptoHelper.encrypt(
            spKeyDefault.deviceEditKey,
            keysDefault.key,
            keysDefault.seed
        ).toSha1()
    }

    override fun save(deviceId: String) {
        val deviceIdEncrypted = cryptoHelper.encrypt(deviceId, keysDefault.key, keysDefault.seed)
        sharedPreferences.edit {
            putString(keyEdit, deviceIdEncrypted)
            commit()
        }
    }

    override fun get(): String {
        val deviceIdSaved = sharedPreferences.getString(keyEdit, "") ?: ""
        if (deviceIdSaved.isEmpty()) {
            return deviceIdSaved
        }

        return cryptoHelper.decrypt(deviceIdSaved, keysDefault.key, keysDefault.seed)
    }

    override fun clean() {
        sharedPreferences.edit {
            putString(keyEdit, "")
            commit()
        }
    }

}