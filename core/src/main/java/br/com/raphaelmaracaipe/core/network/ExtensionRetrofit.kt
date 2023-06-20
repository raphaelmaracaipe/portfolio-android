package br.com.raphaelmaracaipe.core.network

import br.com.raphaelmaracaipe.core.data.api.response.ErrorResponse
import br.com.raphaelmaracaipe.core.extensions.fromJSON
import com.google.gson.JsonParser
import okhttp3.ResponseBody
import org.json.JSONObject

fun ResponseBody.getCodeOfErrorBody(): Int {
    val bodyError = string().fromJSON<ErrorResponse>()
    return bodyError.codeError
}