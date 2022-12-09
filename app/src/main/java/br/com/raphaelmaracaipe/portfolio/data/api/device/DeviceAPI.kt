package br.com.raphaelmaracaipe.portfolio.data.api.device

import br.com.raphaelmaracaipe.portfolio.data.api.models.request.RequestDeviceInfo

interface DeviceAPI {

    suspend fun sendInformationAboutDevice(requestDeviceInfo: RequestDeviceInfo): Boolean

}