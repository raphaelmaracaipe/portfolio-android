package br.com.raphaelmaracaipe.portfolio.di

import android.content.Context
import br.com.raphaelmaracaipe.portfolio.data.db.di.DataBaseModule
import br.com.raphaelmaracaipe.portfolio.ui.userLogin.UserLoginSubcomponent
import br.com.raphaelmaracaipe.portfolio.ui.userRegister.UserRegisterSubcomponent
import dagger.BindsInstance
import dagger.Component
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

    fun loginSubcomponent(): UserLoginSubcomponent.Factory
    fun userRegisterSubcomponent(): UserRegisterSubcomponent.Factory

}