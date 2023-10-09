package br.com.raphaelmaracaipe.core.data.sp

import android.content.Context
import androidx.core.content.edit

class SeedSPImpl(
    context: Context
) : SeedSP {

    private val sp = context.getSharedPreferences("SEED_KEEY", Context.MODE_PRIVATE)
    private val key = "SEED_KEEY"

    override fun save(seed: String) {
        sp.edit {
            putString(key, seed)
            commit()
        }
    }

    override fun clean() {
        save("")
    }

    override fun get() = sp.getString(key, "") ?: ""

}