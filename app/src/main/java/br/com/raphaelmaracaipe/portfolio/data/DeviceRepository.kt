package br.com.raphaelmaracaipe.portfolio.data

import android.util.Log
import br.com.raphaelmaracaipe.portfolio.BuildConfig
import br.com.raphaelmaracaipe.portfolio.const.REGEX_COMMUNICATION
import br.com.raphaelmaracaipe.portfolio.data.api.device.DeviceAPI
import br.com.raphaelmaracaipe.portfolio.data.api.di.ApiModule
import br.com.raphaelmaracaipe.portfolio.data.api.models.RequestDeviceInfo
import br.com.raphaelmaracaipe.portfolio.data.sp.di.SharedPreferenceModule
import br.com.raphaelmaracaipe.portfolio.data.sp.token.TokenSP
import br.com.raphaelmaracaipe.portfolio.utils.regex.RegexGenerate
import br.com.raphaelmaracaipe.portfolio.utils.regex.RegexModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeviceRepository @Inject constructor(
    @ApiModule.DeviceApi private val deviceAPI: DeviceAPI,
    @SharedPreferenceModule.Token private val tokenSP: TokenSP,
    @RegexModule.RegexGenerate private val regexGenerate: RegexGenerate
) {

    suspend fun sendInformationAboutDevice() = withContext(Dispatchers.IO) {
        val keyToCommunication = regexGenerate.generateRandom(REGEX_COMMUNICATION)
        val requestDeviceInfo = RequestDeviceInfo(keyToCommunication)

        if(tokenSP.getKeyOfCommunication() != BuildConfig.KEY) {
            return@withContext true
        }

        val returnAfterCall = deviceAPI.sendInformationAboutDevice(requestDeviceInfo)
        if(returnAfterCall) {
            tokenSP.savedKeyOfCommunications(keyToCommunication)
        }

        returnAfterCall
    }

}