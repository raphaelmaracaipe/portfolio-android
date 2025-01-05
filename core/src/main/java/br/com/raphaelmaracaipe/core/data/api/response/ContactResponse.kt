package br.com.raphaelmaracaipe.core.data.api.response

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ContactResponse(
    val name: String = "",
    val phone: String = "",
    val photo: String = ""
) : Parcelable
