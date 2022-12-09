package br.com.raphaelmaracaipe.portfolio.ui.userRegister.di

import androidx.lifecycle.ViewModel
import br.com.raphaelmaracaipe.portfolio.di.ViewModelKey
import br.com.raphaelmaracaipe.portfolio.ui.userRegister.UserRegisterViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface UserRegisterModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserRegisterViewModel::class)
    fun bindUserRegisterViewModel(viewModel: UserRegisterViewModel): ViewModel

}