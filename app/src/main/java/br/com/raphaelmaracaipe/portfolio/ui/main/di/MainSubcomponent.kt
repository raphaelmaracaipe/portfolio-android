package br.com.raphaelmaracaipe.portfolio.ui.main.di

import br.com.raphaelmaracaipe.portfolio.ui.main.MainActivity
import br.com.raphaelmaracaipe.portfolio.utils.events.EventModule
import dagger.Subcomponent

@Subcomponent(modules = [EventModule::class])
interface MainSubcomponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainSubcomponent
    }

    fun inject(mainActivity: MainActivity)

}