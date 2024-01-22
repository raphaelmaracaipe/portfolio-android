package br.com.raphaelmaracaipe.core.data.api.request

import androidx.annotation.Keep

@Keep
data class TokenRefreshRequest(
    val refresh: String
)