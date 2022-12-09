package br.com.raphaelmaracaipe.portfolio.ui.userOptionsLogin.di

import br.com.raphaelmaracaipe.portfolio.ui.userOptionsLogin.UserOptionsLoginFragment
import br.com.raphaelmaracaipe.portfolio.utils.events.EventModule
import dagger.Subcomponent

@Subcomponent(
    modules = [
        UserOptionsLoginModule::class,
        EventModule::class
    ]
)
interface UserOptionsLoginSubcomponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): UserOptionsLoginSubcomponent
    }

    fun inject(fragment: UserOptionsLoginFragment)

}