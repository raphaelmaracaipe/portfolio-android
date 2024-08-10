package br.com.raphaelmaracaipe.portfolio.di

import br.com.raphaelmaracaipe.core.data.CountryRepository
import br.com.raphaelmaracaipe.core.data.HandShakeRepository
import br.com.raphaelmaracaipe.core.data.KeyRepository
import br.com.raphaelmaracaipe.core.data.SeedRepository
import br.com.raphaelmaracaipe.core.data.TokenRepository
import br.com.raphaelmaracaipe.core.data.UserRepository
import br.com.raphaelmaracaipe.portfolio.fragment.SplashViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(FragmentComponent::class)
object SplashUiModule {

    @ViewModelScoped
    @Provides
    fun providerViewModel(
        seedRepository: SeedRepository,
        userRepository: UserRepository,
        handShakeRepository: HandShakeRepository,
        keyRepository: KeyRepository,
        tokenRepository: TokenRepository,
        countryRepository: CountryRepository
    ) = SplashViewModel(
        seedRepository,
        userRepository,
        handShakeRepository,
        keyRepository,
        tokenRepository,
        countryRepository
    )

}