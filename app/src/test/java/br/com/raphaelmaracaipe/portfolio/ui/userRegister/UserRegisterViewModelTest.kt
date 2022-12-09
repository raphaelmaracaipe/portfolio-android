package br.com.raphaelmaracaipe.portfolio.ui.userRegister

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.portfolio.data.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.M])
@RunWith(RobolectricTestRunner::class)
class UserRegisterViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val userRepository: UserRepository = mockk()
    private lateinit var userRegisterViewModel: UserRegisterViewModel

    @Before
    fun setUp() {
        userRegisterViewModel = UserRegisterViewModel(userRepository)
    }

    @Test
    fun `when want register and not exist in system should return information`() = runBlocking {
        coEvery { userRepository.checkIfEmailExist(any()) } returns false

        userRegisterViewModel.checkIfEmailExist("test@teste.com")
        userRegisterViewModel.emailExist.observeForever { exist ->
            Assert.assertFalse(exist)
        }
    }

    @Test
    fun `when want register but exist in system should return information`() = runBlocking {
        coEvery { userRepository.checkIfEmailExist(any()) } returns true

        userRegisterViewModel.checkIfEmailExist("test@teste.com")
        userRegisterViewModel.emailExist.observeForever { exist ->
            Assert.assertTrue(exist)
        }
    }

    @Test
    fun `when want register but api return error should return message error`() = runBlocking {
        val msgError = "Test error"
        coEvery { userRepository.checkIfEmailExist(any()) } throws Exception(msgError)

        userRegisterViewModel.checkIfEmailExist("test@teste.com")
        userRegisterViewModel.errors.observeForever { error ->
            Assert.assertEquals(msgError, error)
        }
    }

}