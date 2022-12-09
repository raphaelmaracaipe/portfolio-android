package br.com.raphaelmaracaipe.portfolio.utils.device

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

@Module
object DeviceModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class DeviceNet

    @Provides
    @DeviceNet
    fun provideDeviceNetwork(context: Context): DeviceNetwork = DeviceNetworkImpl(context)

}