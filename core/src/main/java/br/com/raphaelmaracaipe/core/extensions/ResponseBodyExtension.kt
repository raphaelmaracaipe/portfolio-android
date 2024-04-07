package br.com.raphaelmaracaipe.core.extensions

import br.com.raphaelmaracaipe.core.data.api.response.ErrorResponse
import okhttp3.ResponseBody

fun ResponseBody?.getCodeOfErrorBody(): Int {
    val bodyError = this?.string()?.fromJSON<ErrorResponse>()
    return bodyError?.codeError ?: 0
}