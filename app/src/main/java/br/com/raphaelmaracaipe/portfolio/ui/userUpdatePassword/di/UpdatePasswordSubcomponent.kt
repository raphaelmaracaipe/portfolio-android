package br.com.raphaelmaracaipe.portfolio.ui.userUpdatePassword.di

import br.com.raphaelmaracaipe.portfolio.ui.userUpdatePassword.UpdatePasswordBottomSheet
import br.com.raphaelmaracaipe.portfolio.ui.userLoginWithPassword.di.UserLoginWithPasswordModule
import br.com.raphaelmaracaipe.portfolio.utils.regex.RegexModule
import dagger.Subcomponent

@Subcomponent(
    modules = [
        UserLoginWithPasswordModule::class,
        RegexModule::class
    ]
)
interface UpdatePasswordSubcomponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): UpdatePasswordSubcomponent
    }

    fun inject(fragment: UpdatePasswordBottomSheet)

}