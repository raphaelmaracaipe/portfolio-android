package br.com.raphaelmaracaipe.core.data

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.data.api.ContactApi
import br.com.raphaelmaracaipe.core.data.api.response.ContactResponse
import br.com.raphaelmaracaipe.core.data.db.daos.ContactDAO
import br.com.raphaelmaracaipe.core.data.db.entities.ContactEntity
import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException
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
    fun `when check if exist contact saved in db`() = runBlocking {
        coEvery { contactDAO.countContacts(any()) } returns 1

        val isExist = contactRepository.isExistSaved("test")
        assertTrue(isExist)
    }

    @Test
    fun `when api return erro network should return`() = runBlocking {
        coEvery { contactApi.consult(any()) } throws NetworkException(
            NetworkCodeEnum.ERROR_GENERAL.code
        )

        try {
            contactRepository.consult(arrayListOf("555", "666"))
            assertTrue(false)
        } catch (e: Exception) {
            assertTrue(true)
        }
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
        coEvery { contactApi.consult(any()) } returns arrayListOf(
            ContactResponse(
                "test",
                "=AA",
                "555"
            )
        )

        try {
            val returnConsult = contactRepository.consult(arrayListOf("555", "666"))
            assertEquals(1, returnConsult.size)
        } catch (e: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when contact saved and return success`() = runBlocking {
        coEvery { contactDAO.getContactPagination(any(), any()) } returns listOf(ContactEntity())

        try {
            val consult = contactRepository.contactSaved(1, 0)
            assertEquals(1, consult.size)
        } catch (_: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when contact saved but return error`() = runBlocking {
        coEvery { contactDAO.getContactPagination(any(), any()) } throws Exception("test")

        val returns = contactRepository.contactSaved(1, 0)
        assertEquals(0, returns.size)
    }

    @Test
    fun `when search item and return success`() = runBlocking {
        coEvery { contactDAO.getContactPaginationAndSearch(any()) } returns listOf(ContactEntity())

        try {
            val dataOfConsults = contactRepository.searchItem("t", 1, 0)
            assertEquals(1, dataOfConsults.size)
        } catch (_: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when search item and return error`() = runBlocking {
        coEvery { contactDAO.getContactPaginationAndSearch(any()) } throws Exception("fail")

        val returns = contactRepository.searchItem("t", 1, 0)
        assertEquals(0, returns.size)
    }

    @Test
    fun `when get information about status online contact`() = runBlocking{
        try {
            coEvery { contactDAO.lastSeen(any(), any()) } returns Unit

            contactRepository.lastSeen("999999999")
            assertTrue(true)
        } catch (e: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when consult information about contact`() = runBlocking{
        coEvery { contactDAO.getContact(any()) } returns ContactEntity(phone = "AA")

        val contact = contactRepository.getContact("1234567890")
        assertEquals("AA", contact.phone)
    }
}