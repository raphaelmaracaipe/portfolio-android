package br.com.raphaelmaracaipe.portfolio.ui.userLogin.di

import br.com.raphaelmaracaipe.portfolio.ui.userLogin.UserLoginFragment
import dagger.Subcomponent

@Subcomponent(modules = [UserLoginModule::class])
interface UserLoginSubcomponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): UserLoginSubcomponent
    }

    fun inject(loginFragment: UserLoginFragment)

}