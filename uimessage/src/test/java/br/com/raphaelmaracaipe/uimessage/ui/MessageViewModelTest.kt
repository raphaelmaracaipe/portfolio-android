package br.com.raphaelmaracaipe.uimessage.ui

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.data.StatusRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        context = mockk()
        statusRepository = mockk()

        viewModel = MessageViewModel(
            context = context,
            statusRepository = statusRepository
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

        viewModel.consultStatus("1234567890")
        assertTrue(true)
    }

    @Test
    fun `when send request to consult status but return fail`() = runTest {
        coEvery { statusRepository.checkStatus(any()) } throws Exception("fail")

        viewModel.consultStatus("1234567890")
        assertTrue(true)
    }

}