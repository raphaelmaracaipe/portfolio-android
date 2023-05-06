package br.com.raphaelmaracaipe.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseCodeCountry(
    @SerializedName("country_name")
    val countryName: String? = null,
    @SerializedName("code_country")
    val codeCountry: String? = null,
    @SerializedName("code_ison")
    val codeIson: String? = null,
): Parcelable