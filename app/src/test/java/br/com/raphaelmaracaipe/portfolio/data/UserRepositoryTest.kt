package br.com.raphaelmaracaipe.portfolio.data

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.raphaelmaracaipe.portfolio.data.db.AppDataBase
import br.com.raphaelmaracaipe.portfolio.data.db.entities.UserEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.M])
@RunWith(RobolectricTestRunner::class)
class UserRepositoryTest {

    private lateinit var userRepository: UserRepository
    private lateinit var db: AppDataBase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDataBase::class.java
        ).allowMainThreadQueries().build()

        userRepository = UserRepository(db)
    }

    @Test
    fun `should saved of user in db`() = runBlocking {
        val nameToTest = "Test of name"
        userRepository.saveInDataBase(UserEntity(
            name = nameToTest
        ))

        val users = db.userDAO().listAll()
        Assert.assertEquals(1, users.size)
    }


}