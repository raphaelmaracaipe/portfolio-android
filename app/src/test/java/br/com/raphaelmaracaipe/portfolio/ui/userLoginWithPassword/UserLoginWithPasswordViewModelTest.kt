package br.com.raphaelmaracaipe.portfolio.ui.userLoginWithPassword

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.portfolio.data.UserRepository
import br.com.raphaelmaracaipe.portfolio.models.UserRegisterModel
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
class UserLoginWithPasswordViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val userRepository: UserRepository = mockk()
    private lateinit var userLoginWithPasswordViewModel: UserLoginWithPasswordViewModel

    @Before
    fun setUp() {
        userLoginWithPasswordViewModel = UserLoginWithPasswordViewModel(userRepository)
    }

    @Test
    fun `you make login with google success`() {
        coEvery { userRepository.login(UserRegisterModel("test@test.com")) } returns true
        userLoginWithPasswordViewModel.login(UserRegisterModel("test@test.com"))
        userLoginWithPasswordViewModel.success.observeForever { isSuccess ->
            Assert.assertTrue(isSuccess)
        }
    }

    @Test
    fun `you make login with google but api return error`() {
        val msgError = "Test message of error"
        coEvery { userRepository.login(UserRegisterModel("test@test.com")) } throws Exception(msgError)
        userLoginWithPasswordViewModel.login(UserRegisterModel("test@test.com"))
        userLoginWithPasswordViewModel.errors.observeForever {
            Assert.assertEquals(msgError, it)
        }
    }

}