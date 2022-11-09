package br.com.raphaelmaracaipe.portfolio.ui.userLoginWithPassword.di

import br.com.raphaelmaracaipe.portfolio.ui.userLoginWithPassword.UserLoginWithPasswordFragment
import dagger.Subcomponent

@Subcomponent(
    modules = [UserLoginWithPasswordModule::class]
)
interface UserLoginWithPasswordSubcomponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): UserLoginWithPasswordSubcomponent
    }

    fun inject(userLoginWithPasswordFragment: UserLoginWithPasswordFragment)
}