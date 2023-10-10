package br.com.raphaelmaracaipe.core.data.sp

import android.content.Context
import androidx.core.content.edit
import br.com.raphaelmaracaipe.core.network.utils.KeysDefault
import br.com.raphaelmaracaipe.core.security.CryptoHelper

class DeviceIdSPImpl(
    context: Context,
    keysDefault: KeysDefault,
    private val cryptoHelper: CryptoHelper
) : DeviceIdSP {

    private val sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE)
    private val deviceID = "deviceId"
    private val seedDefault = keysDefault.seed
    private val keyDefault = keysDefault.key

    override fun save(deviceId: String) {
        val deviceIdEncrypted = cryptoHelper.encrypt(deviceId, keyDefault, seedDefault)
        sharedPreferences.edit {
            putString(deviceID, deviceIdEncrypted)
            commit()
        }
    }

    override fun get(): String {
        var deviceIdSaved = sharedPreferences.getString(deviceID, "") ?: ""
        if(deviceIdSaved.isNotEmpty()) {
            deviceIdSaved = cryptoHelper.decrypt(deviceIdSaved, keyDefault, seedDefault)
        }
        return deviceIdSaved
    }

}