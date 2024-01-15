package br.com.raphaelmaracaipe.core.di

import android.content.Context
import br.com.raphaelmaracaipe.core.assets.Assets
import br.com.raphaelmaracaipe.core.assets.AssetsImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    fun provideAssets(
        @ApplicationContext context: Context,
    ): Assets {
        val assetsManager = context.assets
        return AssetsImpl(
            context,
            assetsManager
        )
    }

    @Provides
    fun providerContext(
        @ApplicationContext context: Context
    ): Context {
        return context
    }

}