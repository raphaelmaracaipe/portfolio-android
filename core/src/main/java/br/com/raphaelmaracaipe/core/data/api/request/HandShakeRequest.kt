package br.com.raphaelmaracaipe.core.data.api.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class HandShakeRequest(
    @SerializedName("key")
    val key: String = ""
) : Parcelable