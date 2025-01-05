package br.com.raphaelmaracaipe.core.data.api.request

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ProfileRequest(
    var name: String = "",
    var photo: String = "",
    var reminder: String = ""
) : Parcelable