package br.com.raphaelmaracaipe.portfolio.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import br.com.raphaelmaracaipe.portfolio.data.db.entities.UserEntity

@Dao
interface UserDAO {}