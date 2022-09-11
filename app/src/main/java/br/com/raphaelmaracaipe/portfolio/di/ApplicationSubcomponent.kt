package br.com.raphaelmaracaipe.portfolio.di

import br.com.raphaelmaracaipe.portfolio.ui.login.LoginSubcomponent
import dagger.Module

@Module(subcomponents = [
    LoginSubcomponent::class
])
object ApplicationSubcomponent