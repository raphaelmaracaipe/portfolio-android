package br.com.raphaelmaracaipe.portfolio.data.api.device

import br.com.raphaelmaracaipe.portfolio.data.api.models.RequestDeviceInfo

interface DeviceAPI {

    suspend fun sendInformationAboutDevice(requestDeviceInfo: RequestDeviceInfo): Boolean

}