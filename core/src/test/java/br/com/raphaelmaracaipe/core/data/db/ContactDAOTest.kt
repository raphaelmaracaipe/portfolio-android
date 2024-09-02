package br.com.raphaelmaracaipe.core.data.db

import android.os.Build
import androidx.room.Room
import br.com.raphaelmaracaipe.core.data.db.daos.ContactDAO
import br.com.raphaelmaracaipe.core.data.db.entities.ContactEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class ContactDAOTest {

    private lateinit var db: AppDataBase
    private lateinit var dao: ContactDAO

    @Before
    fun setUp() {
        val context = RuntimeEnvironment.getApplication().applicationContext
        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDataBase::class.java
        ).build()

        dao = db.contactsDAO()
    }

    @Test
    fun `when insert data and get`() = runBlocking {
        val entities = arrayListOf(
            ContactEntity("PHONE", "NAME", "PHOTO")
        )
        dao.insert(entities)

        val datas = dao.getValues()
        Assert.assertEquals(1, datas.size)
    }

}