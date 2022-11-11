package br.com.raphaelmaracaipe.portfolio.ui.userLoginWithPassword.di

import br.com.raphaelmaracaipe.portfolio.ui.userLoginWithPassword.UserLoginWithPasswordFragment
import br.com.raphaelmaracaipe.portfolio.utils.events.EventModule
import dagger.Subcomponent

@Subcomponent(
    modules = [
        UserLoginWithPasswordModule::class,
        EventModule::class
    ]
)
interface UserLoginWithPasswordSubcomponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): UserLoginWithPasswordSubcomponent
    }

    fun inject(userLoginWithPasswordFragment: UserLoginWithPasswordFragment)
}