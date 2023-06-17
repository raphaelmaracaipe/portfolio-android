package br.com.raphaelmaracaipe.core.data.api.response

import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ErrorResponse(
    @SerializedName("statusCode")
    val statusCode: Int,
    @SerializedName("message")
    val codeError: Int
): Parcelable {

    fun toJSON() = Gson().toJson(this) ?: "{}"

}