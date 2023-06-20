package br.com.raphaelmaracaipe.uiauth.ui.countries

import android.content.Context
import android.content.res.AssetManager
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.assets.Assets
import br.com.raphaelmaracaipe.core.assets.AssetsImpl
import br.com.raphaelmaracaipe.uiauth.models.CodeCountry
import com.google.gson.Gson
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
class CountriesViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private lateinit var mContext: Context
    private lateinit var mCountriesViewModel: CountriesViewModel

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
        mContext = RuntimeEnvironment.getApplication().applicationContext

        val jsonString = Gson().toJson(codes)
        val inputStream = jsonString.byteInputStream()
        val assetsManager = mockk<AssetManager>()
        every { assetsManager.open(any()) } returns inputStream
        val mAssets: Assets = AssetsImpl(mContext, assetsManager)

        mCountriesViewModel = CountriesViewModel(mAssets)
    }

    @Test
    fun `when search list of countries and return`() = runBlocking {
        mCountriesViewModel.readInformationAboutCodeOfCountry()
        Thread.sleep(1000)
        mCountriesViewModel.codes.observeForever { codes ->
            assertEquals("Brasil", codes[0].countryName)
        }
    }

}