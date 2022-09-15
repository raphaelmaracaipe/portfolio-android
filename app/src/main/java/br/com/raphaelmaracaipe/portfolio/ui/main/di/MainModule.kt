package br.com.raphaelmaracaipe.portfolio.ui.main.di

import androidx.lifecycle.ViewModel
import br.com.raphaelmaracaipe.portfolio.di.ViewModelKey
import br.com.raphaelmaracaipe.portfolio.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MainModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

}