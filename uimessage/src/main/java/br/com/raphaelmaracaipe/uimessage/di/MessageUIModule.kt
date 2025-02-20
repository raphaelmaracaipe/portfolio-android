package br.com.raphaelmaracaipe.uimessage.di

import android.content.Context
import br.com.raphaelmaracaipe.core.data.AuthRepository
import br.com.raphaelmaracaipe.core.data.ContactRepository
import br.com.raphaelmaracaipe.core.data.StatusRepository
import br.com.raphaelmaracaipe.uimessage.ui.MessageViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(FragmentComponent::class)
object MessageUIModule {

    @ViewModelScoped
    @Provides
    fun providerMessageViewModel(
        @ApplicationContext context: Context,
        statusRepository: StatusRepository,
        authRepository: AuthRepository,
        contactRepository: ContactRepository
    ) = MessageViewModel(
        context,
        statusRepository,
        authRepository,
        contactRepository
    )

}