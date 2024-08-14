package br.com.raphaelmaracaipe.core.data

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.assets.Assets
import br.com.raphaelmaracaipe.core.data.db.daos.CodeCountryDAO
import br.com.raphaelmaracaipe.core.data.db.entities.CodeCountryEntity
import com.google.gson.Gson
import io.mockk.coEvery
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
class CountryRepositoryTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private lateinit var assert: Assets
    private lateinit var countryRepository: CountryRepository
    private lateinit var codeCountryDAO: CodeCountryDAO

    @Before
    fun setUp() {
        assert = mockk()
        codeCountryDAO = mockk()
        countryRepository = CountryRepositoryImpl(assert, codeCountryDAO)
    }

    @Test
    fun `when insert countries in db and not exist datas`() = runBlocking {
        coEvery { assert.read(any()) } returns getCountriesInString()
        coEvery { codeCountryDAO.insert(any()) } returns Unit
        coEvery { codeCountryDAO.count() } returns 0

        try {
            countryRepository.insert()
            assertTrue(true)
        } catch (_: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when insert countries in db but exist data saved`() = runBlocking {
        coEvery { codeCountryDAO.count() } returns 1

        try {
            countryRepository.insert()
            assertTrue(true)
        } catch (_: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when obtain countries saved in db with list empty`() = runBlocking {
        coEvery { codeCountryDAO.getAll() } returns listOf()

        val listSaved = countryRepository.getCountries()
        assertEquals(0, listSaved.size)
    }

    @Test
    fun `when obtain countries saved in db with datas`() = runBlocking {
        coEvery { codeCountryDAO.getAll() } returns listOf(CodeCountryEntity(
            countryName = "test unit",
            codeCountry = "999",
            codeIson = "TU"
        ))

        val listSaved = countryRepository.getCountries()
        assertEquals("TU", listSaved[0].codeIson)
    }

    private fun getCountriesInString(): String {
        val listOfCountries = listOf(
            CodeCountryEntity(
                countryName = "test unit",
                codeCountry = "999",
                codeIson = "TU"
            )
        )
        return Gson().toJson(listOfCountries)
    }

}