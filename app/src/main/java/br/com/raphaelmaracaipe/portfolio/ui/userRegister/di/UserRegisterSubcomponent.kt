package br.com.raphaelmaracaipe.portfolio.ui.userRegister.di

import br.com.raphaelmaracaipe.portfolio.ui.userRegister.UserRegisterStepOneFragment
import br.com.raphaelmaracaipe.portfolio.utils.validations.ValidationModule
import dagger.Subcomponent

@Subcomponent(modules = [
    ValidationModule::class,
    UserRegisterModule::class
])
interface UserRegisterSubcomponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): UserRegisterSubcomponent
    }

    fun inject(userRegisterStepOneFragment: UserRegisterStepOneFragment)

}