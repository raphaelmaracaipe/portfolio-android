package br.com.raphaelmaracaipe.portfolio.utils.events

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

@Module
object EventModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Events

    @Provides
    @Events
    fun providerEvent(context: Context): Event = EventImpl(context)


}