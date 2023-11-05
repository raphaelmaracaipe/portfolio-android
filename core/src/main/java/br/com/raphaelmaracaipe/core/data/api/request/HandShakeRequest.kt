package br.com.raphaelmaracaipe.core.data.api.request

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class HandShakeRequest(
    @SerializedName("key")
    val key: String = ""
) : Parcelable