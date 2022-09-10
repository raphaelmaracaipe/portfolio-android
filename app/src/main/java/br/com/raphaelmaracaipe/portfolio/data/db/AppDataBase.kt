package br.com.raphaelmaracaipe.portfolio.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.raphaelmaracaipe.portfolio.BuildConfig
import br.com.raphaelmaracaipe.portfolio.data.db.daos.UserDAO
import br.com.raphaelmaracaipe.portfolio.data.db.entities.UserEntity

@Database(
    entities = [
        UserEntity::class
    ], version = BuildConfig.VERSION_CODE
)
abstract class AppDataBase: RoomDatabase() {

    abstract fun userDAO(): UserDAO

}