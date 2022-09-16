package br.com.raphaelmaracaipe.portfolio.ui.userLogin

import android.os.Build
import br.com.raphaelmaracaipe.portfolio.data.UserRepository
import br.com.raphaelmaracaipe.portfolio.data.db.entities.UserEntity
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.M])
@RunWith(RobolectricTestRunner::class)
class UserLoginViewModelTest {

    private lateinit var userRepository: UserRepository
    private lateinit var userLoginViewModel: UserLoginViewModel

    @Before
    fun setUp() {
        userRepository = mockk()
        userLoginViewModel = UserLoginViewModel(userRepository)
    }

    @Test
    fun `when register user login in db and return error`() = runBlocking {
        coEvery { userRepository.saveInDataBase(any()) }
        userLoginViewModel.registerLoginInDatabase(UserEntity())
        userLoginViewModel.success.observeForever { isSuccess ->
            Assert.assertFalse(isSuccess)
        }
    }

    @Test
    fun `when register user login in db and return success`() = runBlocking {
        coEvery { userRepository.saveInDataBase(any()) } returns Unit
        userLoginViewModel.registerLoginInDatabase(UserEntity())
        userLoginViewModel.success.observeForever { isSuccess ->
            Assert.assertTrue(isSuccess)
        }
    }

}