package br.com.raphaelmaracaipe.portfolio.data.api.user

import android.content.Context
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.data.api.retrofit.ConfigurationServer
import br.com.raphaelmaracaipe.portfolio.data.api.retrofit.enums.CodeError.GENERAL_ERROR
import br.com.raphaelmaracaipe.portfolio.data.api.retrofit.enums.CodeError.USER_EMAIL_INVALID
import br.com.raphaelmaracaipe.portfolio.data.api.retrofit.enums.translateIntegerToEnum
import br.com.raphaelmaracaipe.portfolio.data.api.retrofit.models.HttpError
import br.com.raphaelmaracaipe.portfolio.data.api.retrofit.service.UserService
import br.com.raphaelmaracaipe.portfolio.utils.device.DeviceModule
import br.com.raphaelmaracaipe.portfolio.utils.device.DeviceNetwork
import com.google.gson.Gson
import javax.inject.Inject

class UserAPIImpl(
    private val context: Context,
    private val configurationServer: ConfigurationServer,
    private val deviceNetwork: DeviceNetwork
) : UserAPI {

    override suspend fun checkIfEmailExist(email: String): Boolean {
        if(!deviceNetwork.isNetworkConnected()) {
            throw Exception(context.getString(R.string.err_not_connection_internet))
        }

        var resID = R.string.err_general_server
        try {
            val returnAfterOfExecution = configurationServer.create(
                UserService::class.java
            ).userConsultEmail(email)

            if (returnAfterOfExecution.isSuccessful) {
                return returnAfterOfExecution.body()?.isExist ?: false
            }

            val bodyError = returnAfterOfExecution.errorBody()?.string() ?: ""
            resID = when (codeError(bodyError)) {
                USER_EMAIL_INVALID -> R.string.err_email_invalid
                else -> R.string.err_general_server
            }
        } catch (e: Exception) {
            e.printStackTrace()
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