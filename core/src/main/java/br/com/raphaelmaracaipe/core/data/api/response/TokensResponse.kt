package br.com.raphaelmaracaipe.core.data.api.response

import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TokensResponse(
    @SerializedName("refreshToken")
    val refreshToken: String = "",
    @SerializedName("accessToken")
    val accessToken: String = ""
) : Parcelable {

    fun toJSON() = Gson().toJson(this) ?: "{}"

}