package br.com.raphaelmaracaipe.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.raphaelmaracaipe.core.BuildConfig
import br.com.raphaelmaracaipe.core.data.db.daos.CodeCountryDAO
import br.com.raphaelmaracaipe.core.data.db.entities.CodeCountryEntity

@Database(
    entities = [
        CodeCountryEntity::class
    ], version = BuildConfig.DATABASE_VERSION
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun codeCountryDao(): CodeCountryDAO

}