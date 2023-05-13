package br.com.raphaelmaracaipe.core.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.data.api.UserApi
import br.com.raphaelmaracaipe.core.data.api.response.UserSendCodeResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class UserRepositoryTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @Test
    fun `when request to server and api return true`() = runBlocking {
        val userApi = mockk<UserApi>()
        coEvery { userApi.sendCode(any()) } returns true

        val userRepository: UserRepository = UserRepositoryImpl(userApi)
        val returnApi = userRepository.sendCode(UserSendCodeResponse("9999999999999"))
        Assert.assertTrue(returnApi)
    }

    @Test
    fun `when request to server but api return error`() = runBlocking {
        val msgError = "error in the request"

        val userApi = mockk<UserApi>()
        coEvery { userApi.sendCode(any()) } throws Exception(msgError)

        val userRepository: UserRepository = UserRepositoryImpl(userApi)
        try {
            userRepository.sendCode(UserSendCodeResponse("9999999999999"))
            Assert.assertTrue(true)
        } catch (e: Exception) {
            Assert.assertEquals(msgError, e.message)
        }
    }

}