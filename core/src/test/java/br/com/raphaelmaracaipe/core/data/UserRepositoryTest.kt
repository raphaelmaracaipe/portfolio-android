package br.com.raphaelmaracaipe.core.data

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.data.api.UserApi
import br.com.raphaelmaracaipe.core.data.api.UserApiImpl
import br.com.raphaelmaracaipe.core.data.api.request.ProfileRequest
import br.com.raphaelmaracaipe.core.data.api.request.UserSendCodeRequest
import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse
import br.com.raphaelmaracaipe.core.data.sp.ProfileSP
import br.com.raphaelmaracaipe.core.data.sp.TokenSP
import br.com.raphaelmaracaipe.core.externals.KeysDefault
import br.com.raphaelmaracaipe.core.externals.SpKeyDefault
import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum
import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum.*
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException
import br.com.raphaelmaracaipe.core.security.CryptoHelperImpl
import br.com.raphaelmaracaipe.core.utils.Strings
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class UserRepositoryTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private lateinit var userRepository: UserRepository
    private lateinit var userApi: UserApi
    private lateinit var tokenSP: TokenSP
    private lateinit var profileSP: ProfileSP

    @Before
    fun setUp() {
        userApi = mockk()
        tokenSP = mockk()
        profileSP = mockk()

        userRepository = UserRepositoryImpl(
            userApi,
            tokenSP,
            profileSP
        )
    }

    @Test
    fun `when send code valid should return true`() = runBlocking {
        coEvery { userApi.sendCode(any()) } returns true

        try {
            val isValid = userRepository.sendCode(UserSendCodeRequest("test"))
            assertTrue(isValid)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun `when send code invalid should return throws network`() = runBlocking {
        coEvery { userApi.sendCode(any()) } throws NetworkException(USER_SEND_CODE_INVALID.code)

        try {
            userRepository.sendCode(UserSendCodeRequest("test"))
            assertTrue(false)
        } catch (e: NetworkException) {
            assertEquals(USER_SEND_CODE_INVALID.code, e.code)
        } catch (e: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when send code invalid should return throws exception`() = runBlocking {
        coEvery { userApi.sendCode(any()) } throws Exception("is it blocked")

        try {
            userRepository.sendCode(UserSendCodeRequest("test"))
            assertTrue(false)
        } catch (e: NetworkException) {
            assertEquals(ERROR_GENERAL.code, e.code)
        } catch (e: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when send code to valid and return success should return success`() = runBlocking {
        coEvery { userApi.validCode(any()) } returns TokensResponse("refresh", "access")
        coEvery { tokenSP.save(any()) } returns Unit

        try {
            userRepository.validCode("123456")
            assertTrue(true)
        } catch (e: NetworkException) {
            assertTrue(false)
        } catch (e: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when send code invalid should return exception`() = runBlocking {
        coEvery { userApi.validCode(any()) } throws NetworkException(USER_SEND_CODE_INVALID.code)

        try {
            userRepository.validCode("123456")
            assertTrue(true)
        } catch (e: NetworkException) {
            assertEquals(USER_SEND_CODE_INVALID.code, e.code)
        } catch (e: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when send code but return exception should return exception`() = runBlocking {
        coEvery { userApi.validCode(any()) } throws Exception("fail")

        try {
            userRepository.validCode("123456")
            assertTrue(true)
        } catch (e: NetworkException) {
            assertEquals(ERROR_GENERAL.code, e.code)
        } catch (e: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when send profile with success should returns profile`() = runBlocking {
        coEvery { userApi.profile(any()) } returns true
        coEvery { profileSP.markWithExistProfile() } returns Unit

        try {
            userRepository.profile(ProfileRequest())
            assertTrue(true)
        } catch (e: NetworkException) {
            assertTrue(false)
        } catch (e: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when send profile but return exception should return exception`() = runBlocking {
        coEvery { userApi.profile(any()) } throws NetworkException(USER_FAIL_TO_INSERT_PROFILE.code)

        try {
            userRepository.profile(ProfileRequest())
            assertTrue(true)
        } catch (e: NetworkException) {
            assertEquals(USER_FAIL_TO_INSERT_PROFILE.code, e.code)
        } catch (e: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when send profile but return exception generic should return exception network`() = runBlocking {
        coEvery { userApi.profile(any()) } throws Exception("fail")

        try {
            userRepository.profile(ProfileRequest())
            assertTrue(true)
        } catch (e: NetworkException) {
            assertEquals(ERROR_GENERAL.code, e.code)
        } catch (e: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when check if profile exist saved`() {
        every { profileSP.isExistProfileSaved() } returns true
        val isExistProfileSaved = userRepository.isExistProfileSaved()
        assertTrue(isExistProfileSaved)
    }

    @Test
    fun `when mark which profile saved`() {
        every { profileSP.markWithExistProfile() } returns Unit
        userRepository.markWhichProfileSaved()
        assertTrue(true)
    }

}