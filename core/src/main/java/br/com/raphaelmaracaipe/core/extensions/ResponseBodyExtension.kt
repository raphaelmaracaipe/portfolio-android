package br.com.raphaelmaracaipe.core.extensions

import br.com.raphaelmaracaipe.core.data.api.response.ErrorResponse
import br.com.raphaelmaracaipe.core.extensions.fromJSON
import okhttp3.ResponseBody

fun ResponseBody.getCodeOfErrorBody(): Int {
    val bodyError = string().fromJSON<ErrorResponse>()
    return bodyError.codeError
}