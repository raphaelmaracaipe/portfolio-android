package br.com.raphaelmaracaipe.portfolio.ui.main.di

import br.com.raphaelmaracaipe.portfolio.ui.main.MainActivity
import dagger.Subcomponent

@Subcomponent(modules = [MainModule::class])
interface MainSubcomponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainSubcomponent
    }

    fun inject(mainActivity: MainActivity)

}