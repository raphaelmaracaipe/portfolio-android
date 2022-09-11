package br.com.raphaelmaracaipe.portfolio.data.db.di

import android.content.Context
import androidx.room.Room
import br.com.raphaelmaracaipe.portfolio.R
import br.com.raphaelmaracaipe.portfolio.data.db.AppDataBase
import br.com.raphaelmaracaipe.portfolio.utils.extensions.removeNonSpacingMarks
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

@Module
object DataBaseModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class DataBase

    @DataBase
    @Provides
    fun provideDataBase(context: Context): AppDataBase = Room.databaseBuilder(
        context,
        AppDataBase::class.java,
        context.resources.getString(R.string.app_name).removeNonSpacingMarks()
    ).build()

}