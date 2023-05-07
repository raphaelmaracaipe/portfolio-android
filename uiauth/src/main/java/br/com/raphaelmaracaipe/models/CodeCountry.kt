package br.com.raphaelmaracaipe.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CodeCountry(
    @SerializedName("country_name")
    val countryName: String? = null,
    @SerializedName("code_country")
    val codeCountry: String? = null,
    @SerializedName("code_ison")
    val codeIson: String? = null,
): Parcelable