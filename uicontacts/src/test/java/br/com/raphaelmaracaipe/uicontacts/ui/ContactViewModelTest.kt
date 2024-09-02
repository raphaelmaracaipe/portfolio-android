package br.com.raphaelmaracaipe.uicontacts.ui

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.os.Build
import android.provider.ContactsContract
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.raphaelmaracaipe.core.data.ContactRepository
import br.com.raphaelmaracaipe.core.data.api.response.ContactResponse
import br.com.raphaelmaracaipe.core.network.enums.NetworkCodeEnum
import br.com.raphaelmaracaipe.core.network.exceptions.NetworkException
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
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

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `sendContactsToConsult should post value when contacts are retrieved successfully`() =
        runTest {
            val contactsList = arrayListOf(ContactResponse(name = "John Doe", phone = "123456789"))
            val contactStrings = arrayListOf("123456789")

            coEvery { contactRepository.consult(contactStrings) } returns contactsList

            mockContactsResolver(contactStrings)

            val observer: Observer<ArrayList<ContactResponse>> = mockk(relaxed = true)
            viewModel.contact.observeForever(observer)

            // Act
            viewModel.sendContactsToConsult()
            advanceUntilIdle()

            // Assert
            verify { observer.onChanged(contactsList) }
        }

    @Test
    fun `sendContactsToConsult should post empty value when network exception occurs`() = runTest {
        // Arrange
        val contactStrings = arrayListOf("123456789")
        coEvery { contactRepository.consult(contactStrings) } throws NetworkException(
            NetworkCodeEnum.ERROR_GENERAL.code
        )

        mockContactsResolver(contactStrings)

        val observer: Observer<ArrayList<ContactResponse>> = mockk(relaxed = true)
        viewModel.contact.observeForever(observer)

        // Act
        viewModel.sendContactsToConsult()
        advanceUntilIdle()

        // Assert
        verify { observer.onChanged(arrayListOf()) }
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
