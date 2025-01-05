package br.com.raphaelmaracaipe.uicontacts.ui

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.os.Build
import android.provider.ContactsContract
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.raphaelmaracaipe.core.data.ContactRepository
import br.com.raphaelmaracaipe.core.data.db.entities.ContactEntity
import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M])
class ContactViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var context: Context
    private lateinit var contactRepository: ContactRepository
    private lateinit var viewModel: ContactViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        context = mockk()
        contactRepository = mockk()
        viewModel = ContactViewModel(context, contactRepository)
    }

    @Test
    fun `sendContactsToConsult should post value when contacts are retrieved successfully`() =
        runTest {
            val contactsList =
                arrayListOf(ContactEntity(name = "John Doe", phone = "123456789"))
            val contactStrings = arrayListOf("123456789")

            coEvery { contactRepository.consult(contactStrings) } returns contactsList

            mockContactsResolver(contactStrings)

            val observer: Observer<ArrayList<ContactEntity>> = mockk(relaxed = true)
            viewModel.contacts.observeForever(observer)

            viewModel.sendContactsToConsult(0, 1)
            advanceUntilIdle()

            assertTrue(true)
        }

    @Test
    fun `sendContactsToConsult should post empty value when network exception occurs`() = runTest {
        val contactStrings = arrayListOf("123456789")
        coEvery { contactRepository.consult(contactStrings) } throws NetworkException(
            NetworkCodeEnum.ERROR_GENERAL.code
        )

        mockContactsResolver(contactStrings)

        val observer: Observer<ArrayList<ContactEntity>> = mockk(relaxed = true)
        viewModel.contacts.observeForever(observer)

        viewModel.sendContactsToConsult(1, 0)
        advanceUntilIdle()
        assertTrue(true)
    }

    @Test
    fun `when consult contact saved and post`() = runTest {
        coEvery { contactRepository.contactSaved(any(), any()) } returns arrayListOf(
            ContactEntity(

            )
        )

        viewModel.contactSaved(1, 0)
        viewModel.contacts.observeForever { contacts ->
            assertEquals(1, contacts.size)
        }
    }

    @Test
    fun `when consult contact saved but return error`() = runTest {
        coEvery { contactRepository.contactSaved(any(), any()) } throws Exception("fail")

        viewModel.contactSaved(1, 0)
        viewModel.contacts.observeForever { contacts ->
            assertEquals(0, contacts.size)
        }
    }

    @Test
    fun `when search item with success`() = runTest {
        coEvery {
            contactRepository.searchItem(
                any(), any(), any()
            )
        } returns arrayListOf(ContactEntity())

        viewModel.searchItem("tes", 1, 0)
        viewModel.contacts.observeForever { contacts ->
            assertEquals(1, contacts.size)
        }
    }

    @Test
    fun `when search item but exception`() = runTest {
        coEvery {
            contactRepository.searchItem(
                any(), any(), any()
            )
        } throws Exception("Fail")

        viewModel.searchItem("tes", 1, 0)
        viewModel.contacts.observeForever { contacts ->
            assertEquals(0, contacts.size)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun mockContactsResolver(contactStrings: ArrayList<String>) {
        val contentResolver = mockk<ContentResolver>()
        val cursor = mockk<Cursor>(relaxed = true)

        every { context.contentResolver } returns contentResolver
        every { contentResolver.query(any(), any(), any(), any(), any()) } returns cursor
        every { cursor.moveToNext() } returnsMany listOf(true, false)
        every { cursor.getInt(any()) } returns 1
        every { cursor.getString(any()) } returns "1"

        val phoneCursor = mockk<Cursor>(relaxed = true)
        every {
            contentResolver.query(
                eq(ContactsContract.CommonDataKinds.Phone.CONTENT_URI),
                any(),
                any(),
                any(),
                any()
            )
        } returns phoneCursor
        every { phoneCursor.moveToNext() } returnsMany listOf(true, false)
        every { phoneCursor.getString(any()) } returns "123456789"
    }
}
