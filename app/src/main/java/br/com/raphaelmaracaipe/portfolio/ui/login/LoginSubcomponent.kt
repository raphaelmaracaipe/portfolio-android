package br.com.raphaelmaracaipe.portfolio.ui.login

import dagger.Subcomponent

@Subcomponent(modules = [LoginModule::class])
interface LoginSubcomponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginSubcomponent
    }

    fun inject(loginFragment: LoginFragment)

}