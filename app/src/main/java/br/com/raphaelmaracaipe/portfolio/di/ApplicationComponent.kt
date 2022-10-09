package br.com.raphaelmaracaipe.portfolio.di

import android.content.Context
import br.com.raphaelmaracaipe.portfolio.data.api.di.ApiModule
import br.com.raphaelmaracaipe.portfolio.data.db.di.DataBaseModule
import br.com.raphaelmaracaipe.portfolio.data.sp.di.SharedPreferenceModule
import br.com.raphaelmaracaipe.portfolio.ui.main.di.MainSubcomponent
import br.com.raphaelmaracaipe.portfolio.ui.userLogin.di.UserLoginSubcomponent
import br.com.raphaelmaracaipe.portfolio.ui.userRegister.di.UserRegisterSubcomponent
import br.com.raphaelmaracaipe.portfolio.utils.device.DeviceModule
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DataBaseModule::class,
        ApiModule::class,
        SharedPreferenceModule::class,
        ViewModelBuilderModule::class,
        ApplicationSubcomponent::class,
        DeviceModule::class
    ]
)
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }

    fun userLoginSubcomponent(): UserLoginSubcomponent.Factory
    fun userRegisterSubcomponent(): UserRegisterSubcomponent.Factory
    fun mainSubcomponent(): MainSubcomponent.Factory
}

@Module(
    subcomponents = [
        UserLoginSubcomponent::class,
        UserRegisterSubcomponent::class,
        MainSubcomponent::class
    ]
)
object ApplicationSubcomponent