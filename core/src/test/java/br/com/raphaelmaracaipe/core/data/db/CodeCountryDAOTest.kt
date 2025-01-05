package br.com.raphaelmaracaipe.core.data.db

import android.os.Build
import androidx.room.Room
import br.com.raphaelmaracaipe.core.data.db.daos.CodeCountryDAO
import br.com.raphaelmaracaipe.core.data.db.entities.CodeCountryEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class CodeCountryDAOTest {

    private lateinit var db: AppDataBase
    private lateinit var dao: CodeCountryDAO

    @Before
    fun setUp() {
        val context = RuntimeEnvironment.getApplication().applicationContext
        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDataBase::class.java
        ).build()

        dao = db.codeCountryDAO()
    }

    @Test
    fun `when insert value and get data`() = runBlocking {
        val entities = listOf(
            CodeCountryEntity("Brazil", "BR"),
            CodeCountryEntity("United States", "US")
        )

        dao.insert(entities)
        val listOfCountries = dao.getAll()
        val count = dao.count()

        assertEquals(entities.size, listOfCountries.size)
        assertEquals(entities.size, count)
    }

    @Test
    fun `when insert value and return with limit and offset`() = runBlocking {
        val entities = listOf(
            CodeCountryEntity("Brazil", "BR"),
            CodeCountryEntity("United States", "US")
        )

        dao.insert(entities)
        val countries = dao.getLimited(1, 0)
        assertNotEquals(0, countries.size)
    }

    @After
    fun tearDown() {
        db.close()
    }

}