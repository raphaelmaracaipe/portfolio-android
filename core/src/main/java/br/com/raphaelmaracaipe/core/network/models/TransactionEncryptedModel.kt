package br.com.raphaelmaracaipe.core.network.models

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class TransactionEncryptedModel(
    val data: String = ""
) : Parcelable