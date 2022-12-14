package br.com.raphaelmaracaipe.portfolio.data

import android.content.Context
import android.os.Build
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.raphaelmaracaipe.portfolio.data.api.user.UserAPI
import br.com.raphaelmaracaipe.portfolio.data.db.AppDataBase
import br.com.raphaelmaracaipe.portfolio.data.sp.token.TokenSP
import br.com.raphaelmaracaipe.portfolio.data.sp.token.TokenSPImpl
import br.com.raphaelmaracaipe.portfolio.data.api.models.response.ResponseTokenModel
import br.com.raphaelmaracaipe.portfolio.models.UserRegisterModel
import br.com.raphaelmaracaipe.portfolio.utils.regex.RegexGenerate
import br.com.raphaelmaracaipe.portfolio.utils.regex.RegexGenerateImpl
import br.com.raphaelmaracaipe.portfolio.utils.security.encryptDecrypt.EncryptDecryptImpl
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
class UserRepositoryTest {

    private lateinit var userRepository: UserRepository
    private lateinit var db: AppDataBase
    private lateinit var userAPI: UserAPI
    private lateinit var tokenSP: TokenSP

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDataBase::class.java
        ).allowMainThreadQueries().build()

        userAPI = mockk()
        tokenSP = TokenSPImpl(context, EncryptDecryptImpl())
        val regexGenerate: RegexGenerate = RegexGenerateImpl()

        userRepository = UserRepository(db, userAPI, tokenSP, regexGenerate)
    }

    @Test
    fun `when check if email is valid should return false`() = runBlocking {
        coEvery { userAPI.checkIfEmailExist(any()) } returns false

        val email = "test"
        val returnInformationAfterCalled = userRepository.checkIfEmailExist(email)
        Assert.assertFalse(returnInformationAfterCalled)
    }

    @Test
    fun `when check if email is valid should return true`() = runBlocking {
        coEvery { userAPI.checkIfEmailExist(any()) } returns true

        val email = "test@test.com"
        val returnInformationAfterCalled = userRepository.checkIfEmailExist(email)
        Assert.assertTrue(returnInformationAfterCalled)
    }

    @Test
    fun `when request code should send to email`() = runBlocking {
        coEvery { userAPI.requestCode(any()) } returns true
        Assert.assertTrue(userRepository.requestCode("test@test.com"))
    }

    @Test
    fun `when request code but api return with error`() = runBlocking {
        coEvery { userAPI.requestCode(any()) } returns false
        Assert.assertFalse(userRepository.requestCode("test@test.com"))
    }

    @Test
    fun `when register user in api with success`() = runBlocking {
        coEvery { userAPI.registerUserInServer(any()) } returns ResponseTokenModel("AAA", "BBB")
        Assert.assertTrue(userRepository.registerUserInServer(UserRegisterModel()))
    }

    @Test
    fun `when register user in api with false`() = runBlocking {
        coEvery { userAPI.registerUserInServer(any()) } returns ResponseTokenModel()
        Assert.assertFalse(userRepository.registerUserInServer(UserRegisterModel()))
    }

    @Test
    fun `when register with google and api return success with token`() = runBlocking {
        coEvery { userAPI.signWithGoogle(any()) } returns ResponseTokenModel("AAA", "BBB")
        Assert.assertTrue(userRepository.signWithGoogle("test@test.com"))
    }

    @Test
    fun `when register with google and api return error`() = runBlocking {
        val msgError = "test"
        coEvery { userAPI.signWithGoogle(any()) } throws Exception(msgError)
        try {
            userRepository.signWithGoogle("test@test.com")
            Assert.assertTrue(false)
        } catch (e: Exception) {
            Assert.assertEquals(msgError, e.message)
        }
    }

    @Test
    fun `when you want login and api return tokens should message to client`() = runBlocking {
        coEvery { userAPI.login(any()) } returns ResponseTokenModel("AAA", "BBB")
        Assert.assertTrue(userRepository.login(UserRegisterModel("test@test.com")))
    }

    @Test
    fun `when you want login but api return error`() = runBlocking {
        coEvery { userAPI.login(any()) } throws Exception("test")
        try {
            userRepository.login(UserRegisterModel("test@test.com"))
            Assert.assertTrue(false)
        } catch (e: Exception) {
            Assert.assertEquals("test", e.message)
        }
    }

    @Test
    fun `when check if exist token saved`() {
        tokenSP.save(ResponseTokenModel("AAA", "BBB"))
        Assert.assertTrue(userRepository.existTokenSaved())
    }

    @Test
    fun `when you forgot password but api return error should message for you`() = runBlocking {
        coEvery { userAPI.forgotPassword(any()) } throws Exception("test")
        try {
            userRepository.forgotPassword("test@test.com")
            Assert.assertTrue(false)
        } catch (e: Exception) {
            Assert.assertEquals("test", e.message)
        }
    }

    @Test
    fun `when user want forgot password and api return success`() = runBlocking {
        coEvery { userAPI.forgotPassword(any()) } returns true
        val returns = userRepository.forgotPassword("test@test.com")
        Assert.assertTrue(returns)
    }

}