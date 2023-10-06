package br.com.raphaelmaracaipe.core.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.data.api.UserApi
import br.com.raphaelmaracaipe.core.data.api.request.UserSendCodeRequest
import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse
import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
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
        val returnApi = userRepository.sendCode(UserSendCodeRequest("9999999999999"))
        assertTrue(returnApi)
    }

    @Test
    fun `when request to server but api return error`() = runBlocking {
        val userApi = mockk<UserApi>()
        coEvery { userApi.sendCode(any()) } throws NetworkException(
            NetworkCodeEnum.ERROR_GENERAL.code
        )

        val userRepository: UserRepository = UserRepositoryImpl(userApi)
        try {
            userRepository.sendCode(UserSendCodeRequest("9999999999999"))
            assertTrue(true)
        } catch (e: NetworkException) {
            assertEquals(NetworkCodeEnum.ERROR_GENERAL.code, e.code)
        }
    }

    @Test
    fun `when request to valid code correct return tokens of communication`() = runBlocking {
        val userApi = mockk<UserApi>()
        coEvery { userApi.validCode(any()) } returns TokensResponse("a", "b")

        val userRepository: UserRepository = UserRepositoryImpl(userApi)
        try {
            val tokens = userRepository.validCode("999999")
            assertEquals("b", tokens.accessToken)
        } catch (e: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when request to valid code incorrect return error`() = runBlocking {
        val userApi = mockk<UserApi>()
        coEvery { userApi.validCode(any()) } throws NetworkException(2001)

        val userRepository: UserRepository = UserRepositoryImpl(userApi)
        try {
            userRepository.validCode("999999")
            assertTrue(false)
        } catch (e: NetworkException) {
            assertEquals(2001, e.code)
        }
    }

}