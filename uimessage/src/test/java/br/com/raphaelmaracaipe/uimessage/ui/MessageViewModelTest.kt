package br.com.raphaelmaracaipe.uimessage.ui

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import br.com.raphaelmaracaipe.core.data.AuthRepository
import br.com.raphaelmaracaipe.core.data.ContactRepository
import br.com.raphaelmaracaipe.core.data.StatusRepository
import br.com.raphaelmaracaipe.uimessage.flow.StatusNotificationFlow
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertFalse
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
class MessageViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var context: Context
    private lateinit var viewModel: MessageViewModel
    private lateinit var statusRepository: StatusRepository
    private lateinit var authRepository: AuthRepository
    private lateinit var contactRepository: ContactRepository

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        statusRepository = mockk()
        authRepository = mockk()
        contactRepository = mockk()

        viewModel = MessageViewModel(
            context = ApplicationProvider.getApplicationContext<Context?>().applicationContext,
            statusRepository = statusRepository,
            authRepository = authRepository,
            contactRepository = contactRepository
        )
    }

    @Test
    fun `when connect with success`() = runTest {
        coEvery { statusRepository.connect() } returns Unit

        viewModel.connect()
        viewModel.doesConnected.observeForever { connected ->
            assertTrue(connected)
        }
    }

    @Test
    fun `when connect with error`() = runTest {
        coEvery { statusRepository.connect() } throws Exception("fail")

        viewModel.connect()
        viewModel.doesConnected.observeForever { connected ->
            assertFalse(connected)
        }
    }

    @Test
    fun `when send request to consult status`() = runTest {
        coEvery { statusRepository.checkStatus(any()) } returns Unit
        coEvery { contactRepository.lastSeen(any()) } returns Unit
        coEvery { statusRepository.onIsHeOnline(any(), any()) } answers {
            secondArg<() -> Unit>().invoke()
        }

        viewModel.consultStatus("1234567890")
        assertTrue(true)
    }

    @Test
    fun `when send request to consult status but return fail`() = runTest {
        coEvery { statusRepository.checkStatus(any()) } throws Exception("fail")

        viewModel.consultStatus("1234567890")
        assertTrue(true)
    }

    @Test
    fun `when create socket with my number to inform status`() = runTest {
        coEvery { statusRepository.onIAmOnline(any()) } returns Unit

        viewModel.iAmOnline()
    }

    @Test
    fun `when receive flow`() = runTest {
        viewModel.returnConsultFlow("1234567890")
        StatusNotificationFlow.notifyStatus()
    }
}