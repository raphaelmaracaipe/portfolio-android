package br.com.raphaelmaracaipe.core.network.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionEncryptedModel(
    val data: String = ""
) : Parcelable