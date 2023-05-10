package br.com.raphaelmaracaipe.uiauth.ui.auth

import android.content.res.AssetManager
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.assets.Assets
import br.com.raphaelmaracaipe.core.assets.AssetsImpl
import br.com.raphaelmaracaipe.uiauth.models.CodeCountry
import br.com.raphaelmaracaipe.uiauth.ui.auth.AuthViewModel
import com.google.gson.Gson
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class AuthViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private lateinit var mAuthViewModel: AuthViewModel
    private val codes = arrayOf(
        CodeCountry(
            countryName = "Brasil",
            codeCountry = "55",
            codeIson = "BR / BRA"
        ),
        CodeCountry(
            countryName = "United States",
            codeCountry = "1",
            codeIson = "US / USA"
        )
    )

    @Before
    fun setUp() {
        val jsonString = Gson().toJson(codes)
        val inputStream = jsonString.byteInputStream()
        val assetsManager = mockk<AssetManager>()
        every { assetsManager.open(any()) } returns inputStream

        val mAssets: Assets = AssetsImpl(assetsManager)
        mAuthViewModel = AuthViewModel(mAssets)
    }

    @Test
    fun `when send code phone should return code`() = runBlocking {
        mAuthViewModel.setTextChangedCodePhone("55")
        mAuthViewModel.codeCountry.observeForever { codeCountry ->
            assertEquals(codes[0].countryName, codeCountry.countryName)
        }
    }

    @Test
    fun `when send code phone but no found code should return null`() {
        mAuthViewModel.setTextChangedCodePhone("33")
        mAuthViewModel.codeCountry.observeForever { codeCountry ->
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

}