package br.com.raphaelmaracaipe.uicontacts.ui

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.test.core.app.ApplicationProvider
import br.com.raphaelmaracaipe.core.data.db.entities.ContactEntity
import br.com.raphaelmaracaipe.uicontacts.databinding.ItemContactBinding
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import io.mockk.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(
    manifest = Config.NONE,
    sdk = [Build.VERSION_CODES.M],
    application = HiltTestApplication::class
)
class ContactAdapterTest {

    private lateinit var adapter: ContactAdapter
    private val mockContext: Context = RuntimeEnvironment.getApplication().applicationContext

    @Before
    fun setup() {
        adapter = ContactAdapter()
    }

    @Test
    fun `onCreateViewHolder should create ViewHolder with correct binding`() {
        val parent = mockk<ViewGroup>(relaxed = true)
        every { parent.context } returns ApplicationProvider.getApplicationContext()

        val viewHolder = adapter.onCreateViewHolder(parent, 0)

        assert(viewHolder is ContactAdapter.ViewHolder)
        assertNotNull(viewHolder.binding)
    }

    @Test
    fun `onBindViewHolder should bind data to ViewHolder`() {
        val mockHolder = mockk<ContactAdapter.ViewHolder>(relaxed = true)
        val contact = ContactEntity("1", "John Doe", "123456789", "photoBase64")

        adapter.submitList(listOf(contact))
        adapter.onBindViewHolder(mockHolder, 0)

        verify { mockHolder.bindView(contact) }
    }

    @Test
    fun `chargeContacts should update the list correctly`() {
        val contact1 = ContactEntity("1", "John Doe", "123456789", "photoBase64")
        val contact2 = ContactEntity("2", "Jane Doe", "987654321", "photoBase64")

        adapter.chargeContacts(arrayListOf(contact1, contact2))

        assertEquals(2, adapter.itemCount)
        assertEquals(contact1, adapter.currentList[0])
        assertEquals(contact2, adapter.currentList[1])
    }
}
