package br.com.raphaelmaracaipe.portfolio.di

import br.com.raphaelmaracaipe.portfolio.ui.userLogin.UserLoginSubcomponent
import dagger.Module

@Module(subcomponents = [
    UserLoginSubcomponent::class
])
object ApplicationSubcomponent