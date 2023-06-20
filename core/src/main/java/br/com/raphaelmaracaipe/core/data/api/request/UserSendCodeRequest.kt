package br.com.raphaelmaracaipe.core.data.api.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserSendCodeRequest(
    @SerializedName("phone")
    val phone: String = ""
) : Parcelable