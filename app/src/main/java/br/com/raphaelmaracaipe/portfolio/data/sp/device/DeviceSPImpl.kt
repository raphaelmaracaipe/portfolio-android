package br.com.raphaelmaracaipe.portfolio.data.sp.device

import android.content.Context
import androidx.core.content.edit
import br.com.raphaelmaracaipe.portfolio.BuildConfig
import br.com.raphaelmaracaipe.portfolio.utils.security.encryptDecrypt.EncryptDecrypt
import java.util.*

class DeviceSPImpl(
    private val context: Context,
    private val encryptDecrypt: EncryptDecrypt
) : DeviceSP {

    private val KEY = "DEVICE_KEY"
    private val DEVICE_UUID = "DEVICE_UUID"

    override fun getUUID(): String {
        val uuidEncrypted = getValueOfSharedPreference()
        var uuid = if (uuidEncrypted.isNotEmpty()) {
            encryptDecrypt.decryptMessage(uuidEncrypted, BuildConfig.KEY)
        } else {
            ""
        }

        if (uuid.isBlank()) {
            uuid = UUID.randomUUID().toString()
            saveUUID(uuid)
        }

        return uuid
    }

    override fun clearAll() {
        context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit {
            clear()
        }
    }

    private fun getValueOfSharedPreference() = context.getSharedPreferences(
        KEY, Context.MODE_PRIVATE
    ).getString(DEVICE_UUID, "") ?: ""

    private fun saveUUID(uuidSaved: String) {
        val uuidEncrypted = encryptDecrypt.encryptMessage(uuidSaved, BuildConfig.KEY)
        context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit {
            putString(DEVICE_UUID, uuidEncrypted)
        }
    }

}