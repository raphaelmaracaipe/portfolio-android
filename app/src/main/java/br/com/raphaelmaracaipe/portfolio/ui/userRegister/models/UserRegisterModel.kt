package br.com.raphaelmaracaipe.portfolio.ui.userRegister.models

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class UserRegisterModel(
    val email: String = "",
    var password: String = "",
    var code: String = ""
): Parcelable
