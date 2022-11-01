package br.com.raphaelmaracaipe.portfolio.ui.userLogin.di

import br.com.raphaelmaracaipe.portfolio.ui.userLogin.UserLoginFragment
import br.com.raphaelmaracaipe.portfolio.ui.userLogin.UserLoginViewModel
import br.com.raphaelmaracaipe.portfolio.utils.events.EventModule
import dagger.Subcomponent

@Subcomponent(modules = [
    UserLoginModule::class,
    EventModule::class
])
interface UserLoginSubcomponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): UserLoginSubcomponent
    }

    fun inject(loginFragment: UserLoginFragment)

}