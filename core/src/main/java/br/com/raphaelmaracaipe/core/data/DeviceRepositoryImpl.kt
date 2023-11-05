package br.com.raphaelmaracaipe.core.data

import br.com.raphaelmaracaipe.core.consts.REGEX_DEVICE_ID
import br.com.raphaelmaracaipe.core.data.sp.DeviceIdSP
import com.github.curiousoddman.rgxgen.RgxGen

class DeviceRepositoryImpl(
    private val deviceIdSP: DeviceIdSP
) : DeviceRepository {

    override fun getDeviceIDSaved(): String = deviceIdSP.get().ifEmpty {
        val deviceIdGenerated = RgxGen(REGEX_DEVICE_ID).generate() ?: ""
        deviceIdSP.save(deviceIdGenerated)
        deviceIdGenerated
    }

}