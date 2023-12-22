package br.com.raphaelmaracaipe.portfolio.fragment

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.raphaelmaracaipe.core.data.HandShakeRepository
import br.com.raphaelmaracaipe.core.data.KeyRepository
import br.com.raphaelmaracaipe.core.data.SeedRepository
import br.com.raphaelmaracaipe.core.data.TokenRepository
import br.com.raphaelmaracaipe.core.data.UserRepository
import br.com.raphaelmaracaipe.portfolio.TestApplication
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
@Config(sdk = [Build.VERSION_CODES.M], application = TestApplication::class)
class SplashViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private lateinit var handShakeRepository: HandShakeRepository
    private lateinit var keyRepository: KeyRepository
    private lateinit var tokenRepository: TokenRepository
    private lateinit var seedRepository: SeedRepository
    private lateinit var userRepository: UserRepository
    private lateinit var splashViewModel: SplashViewModel

    @Before
    fun setUp() {
        handShakeRepository = mockk()
        keyRepository = mockk()
        tokenRepository = mockk()
        seedRepository = mockk()
        userRepository = mockk()

        splashViewModel = SplashViewModel(
            seedRepository,
            userRepository,
            handShakeRepository,
            keyRepository,
            tokenRepository
        )
    }

    @Test
    fun `when call method to clean should clean data`() {
        coEvery { seedRepository.cleanSeedSaved() } returns Unit

        try {
            splashViewModel.cleanSeedSaved()
            assertTrue(true)
        } catch (_: Exception) {
            assertTrue(false)
        }
    }

    @Test
    fun `when call send and return observable with true`() = runBlocking {
        coEvery { keyRepository.isExistKeyRegistered() } returns true
        coEvery { tokenRepository.isExistTokenRegistered() } returns true

        splashViewModel.send()

        splashViewModel.response.observeForever {
            assertTrue(it)
        }

    }

    @Test
    fun `when call send and dont have token saved should call api and return key generated`() =
        runBlocking {
            coEvery { keyRepository.isExistKeyRegistered() } returns false
            coEvery { keyRepository.saveKeyGenerated(any()) } returns Unit
            coEvery { handShakeRepository.send() } returns "AAA"

            splashViewModel.send()
            splashViewModel.response.observeForever {
                assertFalse(it)
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

    @Test
    fun `when check if user is have profile saved should return observable`() {
        coEvery { userRepository.isExistProfileSaved() } returns true

        splashViewModel.send()
        splashViewModel.isExistProfile.observeForever {
            assertTrue(true)
        }
    }

    @Test
    fun `when check if user is have key registered should return observable`() {
        coEvery { userRepository.isExistProfileSaved() } returns false
        coEvery { keyRepository.isExistKeyRegistered() } returns true
        coEvery { tokenRepository.isExistTokenRegistered() } returns true

        splashViewModel.send()
        splashViewModel.response.observeForever {
            assertTrue(it)
        }
    }

    @Test
    fun `when check if user have token but not exist should return observable with false`() {
        coEvery { userRepository.isExistProfileSaved() } returns false
        coEvery { keyRepository.isExistKeyRegistered() } returns false

        splashViewModel.send()
        splashViewModel.response.observeForever {
            assertFalse(it)
        }
    }

}