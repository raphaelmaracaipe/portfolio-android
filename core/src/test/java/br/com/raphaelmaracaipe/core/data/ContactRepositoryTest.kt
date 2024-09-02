package br.com.raphaelmaracaipe.core.data

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.data.api.ContactApi
import br.com.raphaelmaracaipe.core.data.api.response.ContactResponse
import br.com.raphaelmaracaipe.core.data.db.daos.ContactDAO
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
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class ContactRepositoryTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private lateinit var contactRepository: ContactRepository
    private lateinit var contactApi: ContactApi
    private lateinit var contactDAO: ContactDAO

    @Before
    fun setUP() {
        contactApi = mockk()
        contactDAO = mockk()

        contactRepository = ContactRepositoryImpl(contactApi, contactDAO)
    }

    @Test
    fun `when api return erro should return`() = runBlocking {
        coEvery { contactApi.consult(any()) } throws Exception("fail")

        try {
            contactRepository.consult(arrayListOf("555", "666"))
            assertTrue(false)
        } catch (e: Exception) {
            assertTrue(true)
        }
    }

    @Test
    fun `when api and return success`() = runBlocking {
        coEvery { contactDAO.insert(any()) } returns Unit
        coEvery { contactApi.consult(any()) } returns arrayListOf(ContactResponse(
            "test",
            "=AA",
            "555"
        ))

        try {
            val returnConsult = contactRepository.consult(arrayListOf("555", "666"))
            assertEquals(1, returnConsult.size)
        } catch (e: Exception) {
            assertTrue(false)
        }
    }


}