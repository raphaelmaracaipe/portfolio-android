package br.com.raphaelmaracaipe.portfolio.ui.userLogin

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.portfolio.data.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.M])
@RunWith(RobolectricTestRunner::class)
class UserLoginViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val userRepository: UserRepository = mockk()
    private lateinit var userLoginViewModel: UserLoginViewModel

    @Before
    fun setUp() {
        userLoginViewModel = UserLoginViewModel(userRepository)
    }

    @Test
    fun `you check if exist token saved in db and return true`() {
        coEvery { userRepository.existTokenSaved() } returns true
        val isTokenSaved = userLoginViewModel.existTokenSaved()
        Assert.assertTrue(isTokenSaved)
    }

    @Test
    fun `you check if exist token saved in db and return false`() {
        coEvery { userRepository.existTokenSaved() } returns false
        val isTokenSaved = userLoginViewModel.existTokenSaved()
        Assert.assertFalse(isTokenSaved)
    }

    @Test
    fun `you login with google should return true`() {
        coEvery { userRepository.signWithGoogle("test@test.com") } returns true
        userLoginViewModel.signWithGoogle("test@test.com")
        userLoginViewModel.afterCallToRegister.observeForever { isSuccess ->
            Assert.assertTrue(isSuccess)
        }
    }

    @Test
    fun `you login with google but return error should return message error`() {
        val msgError = "test message of error"
        coEvery { userRepository.signWithGoogle("test@test.com") } throws Exception(msgError)
        userLoginViewModel.signWithGoogle("test@test.com")
        userLoginViewModel.errors.observeForever {
            Assert.assertEquals(msgError, it)
        }
    }

}