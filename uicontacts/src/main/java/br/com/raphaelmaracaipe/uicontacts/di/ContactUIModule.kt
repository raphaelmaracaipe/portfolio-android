package br.com.raphaelmaracaipe.uicontacts.di

import android.content.Context
import br.com.raphaelmaracaipe.core.data.ContactRepository
import br.com.raphaelmaracaipe.uicontacts.ui.ContactViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(FragmentComponent::class)
object ContactUIModule {

    @ViewModelScoped
    @Provides
    fun providerContactViewModel(
        @ApplicationContext context: Context,
        contactRepository: ContactRepository
    ) = ContactViewModel(
        context,
        contactRepository
    )

}