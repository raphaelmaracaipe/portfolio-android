package br.com.raphaelmaracaipe.portfolio.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.data.db.AppDataBase
import br.com.raphaelmaracaipe.portfolio.utils.extensions.removeNonSpacingMarks
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class DataBase

    @DataBase
    @Provides
    fun provideAppDataBase(
        @ApplicationContext context: Context
    ): AppDataBase {
        val nameOfDB = context.resources.getString(
            R.string.app_name
        ).removeNonSpacingMarks().uppercase()

        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            nameOfDB
        ).build()
    }

}