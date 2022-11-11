package br.com.raphaelmaracaipe.portfolio.ui.userOptionsLogin.di

import androidx.lifecycle.ViewModel
import br.com.raphaelmaracaipe.portfolio.di.ViewModelKey
import br.com.raphaelmaracaipe.portfolio.ui.userOptionsLogin.UserOptionsLoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface UserOptionsLoginModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserOptionsLoginViewModel::class)
    fun bindUserLoginViewModel(viewModel: UserOptionsLoginViewModel): ViewModel

}