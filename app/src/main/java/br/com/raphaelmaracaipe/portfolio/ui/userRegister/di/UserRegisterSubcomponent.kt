package br.com.raphaelmaracaipe.portfolio.ui.userRegister.di

import br.com.raphaelmaracaipe.portfolio.ui.userRegister.UserRegisterStepOneFragment
import br.com.raphaelmaracaipe.portfolio.ui.userRegister.UserRegisterStepThreeFragment
import br.com.raphaelmaracaipe.portfolio.ui.userRegister.UserRegisterStepTwoFragment
import br.com.raphaelmaracaipe.portfolio.utils.events.EventModule
import br.com.raphaelmaracaipe.portfolio.utils.validations.ValidationModule
import dagger.Subcomponent

@Subcomponent(modules = [
    ValidationModule::class,
    UserRegisterModule::class,
    EventModule::class
])
interface UserRegisterSubcomponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): UserRegisterSubcomponent
    }

    fun inject(userRegisterStepOneFragment: UserRegisterStepOneFragment)
    fun inject(userRegisterStepTwoFragment: UserRegisterStepTwoFragment)
    fun inject(userRegisterStepThreeFragment: UserRegisterStepThreeFragment)

}