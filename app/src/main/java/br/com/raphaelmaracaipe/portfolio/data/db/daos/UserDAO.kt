package br.com.raphaelmaracaipe.portfolio.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import br.com.raphaelmaracaipe.portfolio.data.db.entities.UserEntity

@Dao
interface UserDAO {

    @Insert(onConflict = REPLACE)
    suspend fun insert(user: UserEntity)

    @Query("SELECT * FROM users")
    suspend fun listAll(): List<UserEntity>

}