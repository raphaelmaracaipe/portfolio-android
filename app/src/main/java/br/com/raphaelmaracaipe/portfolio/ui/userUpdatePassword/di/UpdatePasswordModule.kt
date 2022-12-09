package br.com.raphaelmaracaipe.portfolio.ui.userUpdatePassword.di

import androidx.lifecycle.ViewModel
import br.com.raphaelmaracaipe.portfolio.di.ViewModelKey
import br.com.raphaelmaracaipe.portfolio.ui.userUpdatePassword.UpdatePasswordViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface UpdatePasswordModule {

    @Binds
    @IntoMap
    @ViewModelKey(UpdatePasswordViewModel::class)
    fun bind(viewModel: UpdatePasswordViewModel): ViewModel

}