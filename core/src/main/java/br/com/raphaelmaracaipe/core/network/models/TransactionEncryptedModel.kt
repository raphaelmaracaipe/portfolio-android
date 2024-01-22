package br.com.raphaelmaracaipe.core.network.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class TransactionEncryptedModel(
    val data: String = ""
) : Parcelable {
    fun toJSON(): String = Gson().toJson(this) ?: "{}"
}