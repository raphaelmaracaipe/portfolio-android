package br.com.raphaelmaracaipe.uiauth.ui.auth

import android.content.Context
import android.content.res.AssetManager
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.assets.Assets
import br.com.raphaelmaracaipe.core.assets.AssetsImpl
import br.com.raphaelmaracaipe.core.data.UserRepository
import br.com.raphaelmaracaipe.uiauth.R
import br.com.raphaelmaracaipe.core.data.db.entities.CodeCountryEntity
import br.com.raphaelmaracaipe.uiauth.data.sp.AuthSPImpl
import com.google.gson.Gson
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
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
class AuthViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private lateinit var mContext: Context
    private lateinit var mAuthViewModel: AuthViewModel
    private lateinit var mUserRepository: UserRepository
    private val codes = arrayOf(
        CodeCountryEntity(
            countryName = "Brasil",
            codeCountry = "55",
            codeIson = "BR / BRA"
        ),
        CodeCountryEntity(
            countryName = "United States",
            codeCountry = "1",
            codeIson = "US / USA"
        )
    )

    @Before
    fun setUp() {
        mContext = RuntimeEnvironment.getApplication().applicationContext

        val jsonString = Gson().toJson(codes)
        val inputStream = jsonString.byteInputStream()
        val assetsManager = mockk<AssetManager>()
        every { assetsManager.open(any()) } returns inputStream

        val mAssets: Assets = AssetsImpl(mContext, assetsManager)
        mUserRepository = mockk()

        mAuthViewModel = AuthViewModel(
            mContext,
            mAssets,
            AuthSPImpl(mContext),
            mUserRepository
        )
    }

    @Test
    fun `when send code phone should return code`() = runBlocking {
        mAuthViewModel.readInformationAboutCodeOfCountry()
        Thread.sleep(1000)

        mAuthViewModel.setTextChangedCodePhone("55")
        mAuthViewModel.codeCountryWhenChangeCodePhone.observeForever { codeCountry ->
            assertEquals(codes[0].countryName, codeCountry.countryName)
        }
    }

    @Test
    fun `when send code phone but no found code should return null`() {
        mAuthViewModel.setTextChangedCodePhone("33")
        mAuthViewModel.codeCountryWhenChangeCodePhone.observeForever { codeCountry ->
            assertEquals(null, codeCountry.codeCountry)
        }
    }

    @Test
    fun `when send text and return false to text not empty`() {
        mAuthViewModel.setTextChangedNumPhone("61982993098")
        mAuthViewModel.isEnableTextCode.observeForever {
            assertFalse(it)
        }
    }

    @Test
    fun `when send to server num of phone should return true`() {
        coEvery { mUserRepository.sendCode(any()) } returns true

        mAuthViewModel.sendCodeToServer("5599999999999")
        mAuthViewModel.sendCodeResponse.observeForever {
            assertTrue(true)
        }
    }

    @Test
    fun `when send to server but api return error should return msg generic`() {
        coEvery { mUserRepository.sendCode(any()) } throws Exception("test")

        mAuthViewModel.sendCodeToServer("5599999999999")
        mAuthViewModel.error.observeForever {
            val text = mContext.getString(R.string.err_request_general)
            assertEquals(text, it)
        }
    }

}