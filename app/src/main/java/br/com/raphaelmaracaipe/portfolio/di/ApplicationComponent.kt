package br.com.raphaelmaracaipe.portfolio.di

import android.content.Context
import br.com.raphaelmaracaipe.portfolio.data.api.di.ApiModule
import br.com.raphaelmaracaipe.portfolio.data.db.di.DataBaseModule
import br.com.raphaelmaracaipe.portfolio.data.sp.di.SharedPreferenceModule
import br.com.raphaelmaracaipe.portfolio.ui.main.di.MainSubcomponent
import br.com.raphaelmaracaipe.portfolio.ui.userLoginWithPassword.di.UserLoginWithPasswordSubcomponent
import br.com.raphaelmaracaipe.portfolio.ui.userOptionsLogin.di.UserOptionsLoginSubcomponent
import br.com.raphaelmaracaipe.portfolio.ui.userRegister.di.UserRegisterSubcomponent
import br.com.raphaelmaracaipe.portfolio.ui.userUpdatePassword.di.UpdatePasswordSubcomponent
import br.com.raphaelmaracaipe.portfolio.utils.device.DeviceModule
import br.com.raphaelmaracaipe.portfolio.utils.regex.RegexModule
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DataBaseModule::class,
        ApiModule::class,
        ViewModelBuilderModule::class,
        ApplicationSubcomponent::class,
        DeviceModule::class,
        SharedPreferenceModule::class,
        RegexModule::class
    ]
)
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }

    fun userOptionsLoginSubcomponent(): UserOptionsLoginSubcomponent.Factory
    fun userLoginWithPasswordSubcomponent(): UserLoginWithPasswordSubcomponent.Factory
    fun userRegisterSubcomponent(): UserRegisterSubcomponent.Factory
    fun mainSubcomponent(): MainSubcomponent.Factory
    fun updateSubcomponent(): UpdatePasswordSubcomponent.Factory
}

@Module(
    subcomponents = [
        UserLoginWithPasswordSubcomponent::class,
        UserOptionsLoginSubcomponent::class,
        UserRegisterSubcomponent::class,
        MainSubcomponent::class,
        UpdatePasswordSubcomponent::class
    ]
)
object ApplicationSubcomponent