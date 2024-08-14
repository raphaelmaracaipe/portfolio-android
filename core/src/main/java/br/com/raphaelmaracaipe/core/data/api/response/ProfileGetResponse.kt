package br.com.raphaelmaracaipe.core.data.api.response

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ProfileGetResponse(
    val name: String = "",
    val photo: String = ""
): Parcelable