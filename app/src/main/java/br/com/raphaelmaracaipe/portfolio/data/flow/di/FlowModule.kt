package br.com.raphaelmaracaipe.portfolio.data.flow.di

import br.com.raphaelmaracaipe.portfolio.data.flow.alertmessage.AlertMessageFlow
import br.com.raphaelmaracaipe.portfolio.data.flow.alertmessage.AlertMessageFlowImpl
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

@Module
object FlowModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class AlertMessage

    @AlertMessage
    @Provides
    fun provideMessage(): AlertMessageFlow = AlertMessageFlowImpl()

}