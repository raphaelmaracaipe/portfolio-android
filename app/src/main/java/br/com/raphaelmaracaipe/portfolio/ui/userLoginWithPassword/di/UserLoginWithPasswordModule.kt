package br.com.raphaelmaracaipe.portfolio.ui.userLoginWithPassword.di

import androidx.lifecycle.ViewModel
import br.com.raphaelmaracaipe.portfolio.di.ViewModelKey
import br.com.raphaelmaracaipe.portfolio.ui.userLoginWithPassword.UserLoginWithPasswordViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface UserLoginWithPasswordModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserLoginWithPasswordViewModel::class)
    fun bind(viewModel: UserLoginWithPasswordViewModel): ViewModel

}