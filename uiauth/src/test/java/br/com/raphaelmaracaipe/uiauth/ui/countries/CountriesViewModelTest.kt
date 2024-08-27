package br.com.raphaelmaracaipe.uiauth.ui.countries

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.data.CountryRepository
import br.com.raphaelmaracaipe.core.data.CountryRepositoryImpl
import br.com.raphaelmaracaipe.core.data.db.entities.CodeCountryEntity
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
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
    private lateinit var mCountryRepository: CountryRepository

    private val codes = listOf(
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
        mCountryRepository = mockk<CountryRepositoryImpl>()
        mCountriesViewModel = CountriesViewModel(mCountryRepository)
    }

    @Test
    fun `when search list of countries and return`() = runBlocking {
        coEvery { mCountryRepository.getCountries() } returns codes

        mCountriesViewModel.readInformationAboutCodeOfCountry()
        mCountriesViewModel.codes.observeForever { codes ->
            assertEquals("Brasil", codes[0].countryName)
        }
    }

    @Test
    fun `when search list of countries but exception`() = runBlocking {
        coEvery { mCountryRepository.getCountries() } throws Exception("error")

        mCountriesViewModel.readInformationAboutCodeOfCountry()
        mCountriesViewModel.codes.observeForever { codes ->
            assertEquals(0, codes.size)
        }
    }


}