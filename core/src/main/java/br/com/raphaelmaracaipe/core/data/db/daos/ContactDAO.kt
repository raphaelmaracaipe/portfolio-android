package br.com.raphaelmaracaipe.core.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.raphaelmaracaipe.core.data.db.entities.ContactEntity

@Dao
interface ContactDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contacts: ArrayList<ContactEntity>)

    @Query("SELECT * FROM contacts")
    suspend fun getValues(): List<ContactEntity>

}