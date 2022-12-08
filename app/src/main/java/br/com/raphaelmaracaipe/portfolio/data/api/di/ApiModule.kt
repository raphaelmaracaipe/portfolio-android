package br.com.raphaelmaracaipe.portfolio.data.api.di

import android.content.Context
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.data.api.device.DeviceAPI
import br.com.raphaelmaracaipe.portfolio.data.api.device.DeviceAPIImpl
import br.com.raphaelmaracaipe.portfolio.data.api.retrofit.ConfigurationServiceImpl
import br.com.raphaelmaracaipe.portfolio.data.api.user.UserAPI
import br.com.raphaelmaracaipe.portfolio.data.api.user.UserAPIImpl
import br.com.raphaelmaracaipe.portfolio.data.sp.device.DeviceSPImpl
import br.com.raphaelmaracaipe.portfolio.data.sp.token.TokenSPImpl
import br.com.raphaelmaracaipe.portfolio.utils.device.DeviceNetworkImpl
import br.com.raphaelmaracaipe.portfolio.utils.regex.RegexGenerateImpl
import br.com.raphaelmaracaipe.portfolio.utils.security.encryptDecrypt.EncryptDecrypt
import br.com.raphaelmaracaipe.portfolio.utils.security.encryptDecrypt.EncryptDecryptImpl
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

@Module
object ApiModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class UserApi

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class DeviceApi

    @UserApi
    @Provides
    fun providerUserAPI(context: Context): UserAPI {
        return UserAPIImpl(
            context,
            getConfiguration(context),
            RegexGenerateImpl()
        )
    }

    @DeviceApi
    @Provides
    fun providerDeviceAPI(context: Context): DeviceAPI {
        return DeviceAPIImpl(
            context,
            getConfiguration(context)
        )
    }

    private fun getConfiguration(context: Context): ConfigurationServiceImpl {
        val encryptDecrypt: EncryptDecrypt = EncryptDecryptImpl()

        return ConfigurationServiceImpl(
            context,
            DeviceNetworkImpl(context),
            encryptDecrypt,
            DeviceSPImpl(
                context,
                encryptDecrypt
            ),
            TokenSPImpl(
                context,
                encryptDecrypt
            ),
            R.string.err_not_connection_internet
        )
    }
}