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

    @Query("SELECT * FROM contacts")
    suspend fun getAll(): List<ContactEntity>

    @Query("SELECT * FROM contacts LIMIT :limitPerPage OFFSET :currentPage")
    suspend fun getContactPagination(limitPerPage: Int, currentPage: Int): List<ContactEntity>

    @Query("SELECT * FROM contacts WHERE name LIKE '%' || :textToSearch || '%' OR phone LIKE '%' || :textToSearch || '%'")
    suspend fun getContactPaginationAndSearch(
        textToSearch: String,
    ): List<ContactEntity>

    @Query("SELECT COUNT(*) FROM contacts WHERE phone = :phone")
    suspend fun countContacts(phone: String): Int

    @Query("UPDATE contacts SET lastSeen = :lastSeen WHERE phone = :phone")
    suspend fun lastSeen(phone: String, lastSeen: Long)

}