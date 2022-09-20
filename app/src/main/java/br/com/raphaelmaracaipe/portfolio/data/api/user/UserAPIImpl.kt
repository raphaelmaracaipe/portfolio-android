package br.com.raphaelmaracaipe.portfolio.data.api.user

import android.content.Context
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.data.api.retrofit.ConfigurationServer
import br.com.raphaelmaracaipe.portfolio.data.api.retrofit.enums.CodeError.GENERAL_ERROR
import br.com.raphaelmaracaipe.portfolio.data.api.retrofit.enums.CodeError.USER_EMAIL_INVALID
import br.com.raphaelmaracaipe.portfolio.data.api.retrofit.enums.translateIntegerToEnum
import br.com.raphaelmaracaipe.portfolio.data.api.retrofit.models.HttpError
import br.com.raphaelmaracaipe.portfolio.data.api.retrofit.service.UserService
import com.google.gson.Gson

class UserAPIImpl(
    private val context: Context,
    private val configurationServer: ConfigurationServer
) : UserAPI {

    override suspend fun checkIfEmailExist(email: String): Boolean {
        val returnAfterOfExecution = configurationServer.create(
            UserService::class.java
        ).userConsultEmail(email)

        if (returnAfterOfExecution.isSuccessful) {
            return returnAfterOfExecution.body()?.isExist ?: false
        }

        val bodyError = returnAfterOfExecution.errorBody()?.string() ?: ""
        val resID = when (codeError(bodyError)) {
            USER_EMAIL_INVALID -> R.string.err_email_invalid
            else -> R.string.err_general_server
        }

        throw Exception(context.getString(resID))
    }

    private fun codeError(bodyError: String) = try {
        val codeError = Gson().fromJson(bodyError, HttpError::class.java)
        translateIntegerToEnum(codeError.code)
    } catch (e: Exception) {
        GENERAL_ERROR
    }

}