package br.com.raphaelmaracaipe.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.raphaelmaracaipe.core.BuildConfig
import br.com.raphaelmaracaipe.core.data.db.daos.CodeCountryDAO
import br.com.raphaelmaracaipe.core.data.db.daos.ContactDAO
import br.com.raphaelmaracaipe.core.data.db.entities.CodeCountryEntity
import br.com.raphaelmaracaipe.core.data.db.entities.ContactEntity
import br.com.raphaelmaracaipe.core.data.db.entities.MessageEntity

@Database(
    entities = [
        CodeCountryEntity::class,
        ContactEntity::class,
        MessageEntity::class
    ], version = BuildConfig.DATABASE_VERSION
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun codeCountryDAO(): CodeCountryDAO

    abstract fun contactsDAO(): ContactDAO

}