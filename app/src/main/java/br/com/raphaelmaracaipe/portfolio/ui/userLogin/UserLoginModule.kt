package br.com.raphaelmaracaipe.portfolio.ui.userLogin

import androidx.lifecycle.ViewModel
import br.com.raphaelmaracaipe.portfolio.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface UserLoginModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserLoginViewModel::class)
    fun bindLoginViewModel(viewModel: UserLoginViewModel): ViewModel

}