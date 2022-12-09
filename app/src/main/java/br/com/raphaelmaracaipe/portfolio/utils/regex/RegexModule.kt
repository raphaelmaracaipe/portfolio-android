package br.com.raphaelmaracaipe.portfolio.utils.regex

import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

@Module
object RegexModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RegexGenerate

    @Provides
    @RegexGenerate
    fun providerRegex(): br.com.raphaelmaracaipe.portfolio.utils.regex.RegexGenerate = RegexGenerateImpl()

}