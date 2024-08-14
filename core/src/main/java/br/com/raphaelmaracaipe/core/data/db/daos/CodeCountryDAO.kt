package br.com.raphaelmaracaipe.core.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.raphaelmaracaipe.core.data.db.entities.CodeCountryEntity

@Dao
interface CodeCountryDAO {

    @Query("SELECT * FROM code_countries")
    suspend fun getAll(): List<CodeCountryEntity>

    @Query("SELECT COUNT(*) FROM code_countries")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(codeCountries: List<CodeCountryEntity>)

    @Query("SELECT * FROM code_countries order by country_name Limit :limit OFFSET :offset")
    suspend fun getLimited(limit: Int, offset: Int): List<CodeCountryEntity>

}