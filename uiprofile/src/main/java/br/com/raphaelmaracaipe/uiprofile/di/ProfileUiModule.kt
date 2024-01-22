package br.com.raphaelmaracaipe.uiprofile.di

import android.content.Context
import br.com.raphaelmaracaipe.core.data.UserRepository
import br.com.raphaelmaracaipe.uiprofile.ui.ProfileViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(FragmentComponent::class)
object ProfileUiModule {

    @ViewModelScoped
    @Provides
    fun providerProfileViewModel(
        @ApplicationContext context: Context,
        userRepository: UserRepository
    ) = ProfileViewModel(
        context,
        userRepository
    )

}