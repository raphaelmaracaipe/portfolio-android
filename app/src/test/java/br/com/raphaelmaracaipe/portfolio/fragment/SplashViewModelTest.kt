package br.com.raphaelmaracaipe.portfolio.fragment

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import br.com.raphaelmaracaipe.core.data.HandShakeRepository
import br.com.raphaelmaracaipe.core.data.KeyRepository
import br.com.raphaelmaracaipe.core.data.TokenRepository
import br.com.raphaelmaracaipe.portfolio.TestApplication
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.M], application = TestApplication::class)
class SplashViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val app: TestApplication = ApplicationProvider.getApplicationContext()
    private lateinit var handShakeRepository: HandShakeRepository
    private lateinit var keyRepository: KeyRepository
    private lateinit var tokenRepository: TokenRepository
    private lateinit var splashViewModel: SplashViewModel

    @Before
    fun setUp() {
        handShakeRepository = mockk()
        keyRepository = mockk()
        tokenRepository = mockk()

        splashViewModel = SplashViewModel(
            handShakeRepository,
            keyRepository,
            tokenRepository
        )
    }

    @Test
    fun `when call send and return observable with true`() = runBlocking {
        coEvery { keyRepository.isExistKeyRegistered() } returns true
        coEvery { tokenRepository.isExistTokenRegistered() } returns true

        app.loadModules(listOf()) {
            splashViewModel.send()
            splashViewModel.response.observeForever {
                assertTrue(it)
            }
        }
    }

    @Test
    fun `when call send and dont have token saved should call api and return key generated`() =
        runBlocking {
            coEvery { keyRepository.isExistKeyRegistered() } returns false
            coEvery { keyRepository.saveKeyGenerated(any()) } returns Unit
            coEvery { handShakeRepository.send() } returns "AAA"

            app.loadModules(listOf()) {
                splashViewModel.send()
                splashViewModel.response.observeForever {
                    assertFalse(it)
                }
            }
        }

    @Test
    fun `when call send but return error should return observable`() = runBlocking {
        coEvery { keyRepository.isExistKeyRegistered() } throws Exception("error message")

        splashViewModel.send()
        splashViewModel.errorRequest.observeForever {
            assertTrue(true)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

}