package br.com.raphaelmaracaipe.core.data

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.TestApplication
import br.com.raphaelmaracaipe.core.data.api.UserApi
import br.com.raphaelmaracaipe.core.data.api.request.UserSendCodeRequest
import br.com.raphaelmaracaipe.core.data.api.response.TokensResponse
import br.com.raphaelmaracaipe.core.data.sp.TokenSP
import br.com.raphaelmaracaipe.core.data.sp.TokenSPImpl
import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException
import br.com.raphaelmaracaipe.core.externals.KeysDefault
import br.com.raphaelmaracaipe.core.externals.SpKeyDefault
import br.com.raphaelmaracaipe.core.security.CryptoHelperImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class, sdk = [Build.VERSION_CODES.M])
class UserRepositoryTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private lateinit var tokenSP: TokenSP

    @Before
    fun setUp() {
        val context = RuntimeEnvironment.getApplication().applicationContext
        val cryptoHelper = CryptoHelperImpl()
        val keysDefault = KeysDefault("nDHj82ZWov6r4bnu", "30rBgU6kuVSHPNXX")
        val spKeyDefault = SpKeyDefault("tokenKey", "tokenKeyEdit", "deviceIdKey", "deviceEditKey")

        val userApi = mockk<UserApi>()

        tokenSP = TokenSPImpl(context, keysDefault, spKeyDefault, cryptoHelper)
    }

    @Test
    fun `when request to server and api return true`() = runBlocking {
        val userApi = mockk<UserApi>()
        val tokenSP = mockk<TokenSP>()
        coEvery { userApi.sendCode(any()) } returns true

        val userRepository: UserRepository = UserRepositoryImpl(userApi, tokenSP)
        val returnApi = userRepository.sendCode(UserSendCodeRequest("9999999999999"))
        assertTrue(returnApi)
    }

    @Test
    fun `when request to server but api return error`() = runBlocking {
        val userApi = mockk<UserApi>()
        val tokenSP = mockk<TokenSP>()
        coEvery { userApi.sendCode(any()) } throws NetworkException(
            NetworkCodeEnum.ERROR_GENERAL.code
        )

        val userRepository: UserRepository = UserRepositoryImpl(userApi, tokenSP)
        try {
            userRepository.sendCode(UserSendCodeRequest("9999999999999"))
            assertTrue(true)
        } catch (e: NetworkException) {
            assertEquals(NetworkCodeEnum.ERROR_GENERAL.code, e.code)
        }
    }

    @Test
    fun `when request to server but api return error generic`() = runBlocking {
        val userApi = mockk<UserApi>()
        val tokenSP = mockk<TokenSP>()
        coEvery { userApi.sendCode(any()) } throws Exception("test")

        val userRepository: UserRepository = UserRepositoryImpl(userApi, tokenSP)
        try {
            userRepository.sendCode(UserSendCodeRequest("9999999999999"))
            assertTrue(true)
        } catch (e: NetworkException) {
            assertEquals(NetworkCodeEnum.ERROR_GENERAL.code, e.code)
        }
    }

    @Test
    fun `when request to valid code`() = runBlocking {
        val userApi = mockk<UserApi>()
        coEvery { userApi.validCode(any()) } returns TokensResponse("refresh", "access")

        val userRepository = UserRepositoryImpl(userApi, tokenSP)
        userRepository.validCode("999999")

        assertEquals(tokenSP.get().accessToken, "access")
    }

    @Test
    fun `when request but api return error generic`() = runBlocking {
        val userApi = mockk<UserApi>()
        coEvery { userApi.validCode(any()) } throws Exception("test")

        val userRepository = UserRepositoryImpl(userApi, tokenSP)
        try {
            userRepository.validCode("999999")
            assertTrue(false)
        } catch (e: NetworkException) {
            assertEquals(NetworkCodeEnum.ERROR_GENERAL.code, e.code)
        }
    }

    @Test
    fun `when request but api return error default`() = runBlocking {
        val userApi = mockk<UserApi>()
        coEvery { userApi.validCode(any()) } throws NetworkException(NetworkCodeEnum.SEED_INVALID.code)

        val userRepository = UserRepositoryImpl(userApi, tokenSP)
        try {
            userRepository.validCode("999999")
            assertTrue(false)
        } catch (e: NetworkException) {
            assertEquals(NetworkCodeEnum.SEED_INVALID.code, e.code)
        }
    }

}