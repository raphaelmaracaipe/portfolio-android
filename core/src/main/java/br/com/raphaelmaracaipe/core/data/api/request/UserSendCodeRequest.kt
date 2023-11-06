package br.com.raphaelmaracaipe.core.data.api.request

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class UserSendCodeRequest(
    @SerializedName("phone")
    val phone: String = ""
) : Parcelable