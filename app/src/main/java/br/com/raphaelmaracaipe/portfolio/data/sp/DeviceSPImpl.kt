package br.com.raphaelmaracaipe.portfolio.data.sp

import android.content.Context
import androidx.core.content.edit
import java.util.UUID

class DeviceSPImpl(
    private val context: Context
) : DeviceSP {

    private val KEY = "DEVICE_KEY"
    private val DEVICE_UUID = "DEVICE_UUID"

    override fun getUUID(): String {
        var uuidSaved = context.getSharedPreferences(
            KEY, Context.MODE_PRIVATE
        ).getString(DEVICE_UUID, "") ?: ""

        if (uuidSaved.isBlank()) {
            uuidSaved = UUID.randomUUID().toString()
            context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit {
                putString(DEVICE_UUID, uuidSaved)
            }
        }

        return uuidSaved
    }

}