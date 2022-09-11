package br.com.raphaelmaracaipe.portfolio.ui.userLogin

import dagger.Subcomponent

@Subcomponent(modules = [UserLoginModule::class])
interface UserLoginSubcomponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): UserLoginSubcomponent
    }

    fun inject(loginFragment: UserLoginFragment)

}