package br.com.raphaelmaracaipe.portfolio.di

import android.content.Context
import br.com.raphaelmaracaipe.portfolio.data.db.di.DataBaseModule
import br.com.raphaelmaracaipe.portfolio.ui.userLogin.di.UserLoginSubcomponent
import br.com.raphaelmaracaipe.portfolio.ui.userRegister.di.UserRegisterSubcomponent
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DataBaseModule::class,
        ViewModelBuilderModule::class,
        ApplicationSubcomponent::class
    ]
)
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }

    fun userLoginSubcomponent(): UserLoginSubcomponent.Factory
    fun userRegisterSubcomponent(): UserRegisterSubcomponent.Factory

}

@Module(
    subcomponents = [
        UserLoginSubcomponent::class,
        UserRegisterSubcomponent::class
    ]
)
object ApplicationSubcomponent