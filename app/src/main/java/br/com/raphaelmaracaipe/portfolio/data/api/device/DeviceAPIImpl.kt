package br.com.raphaelmaracaipe.portfolio.data.api.device

import android.content.Context
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.data.api.models.request.RequestDeviceInfo
import br.com.raphaelmaracaipe.portfolio.data.api.retrofit.ConfigurationServer
import br.com.raphaelmaracaipe.portfolio.data.api.retrofit.service.DeviceService

class DeviceAPIImpl(
    private val context: Context,
    private val configurationServer: ConfigurationServer
): DeviceAPI {

    override suspend fun sendInformationAboutDevice(requestDeviceInfo: RequestDeviceInfo): Boolean {
        val returnAfterOfExecution = configurationServer.create(
            DeviceService::class.java
        ).sendInformationAboutDevice(requestDeviceInfo)

        if(returnAfterOfExecution.isSuccessful) {
            return true
        }

        throw Exception(context.getString(R.string.err_general_server))
    }

}